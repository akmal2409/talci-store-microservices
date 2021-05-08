package tech.talci.warehouseservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.talci.warehouseservice.dto.InventoryItemDto;
import tech.talci.warehouseservice.dto.StockValidationRequest;
import tech.talci.warehouseservice.dto.StockValidationResponse;
import tech.talci.warehouseservice.events.ProductAddedEvent;
import tech.talci.warehouseservice.events.StockUpdateEvent;
import tech.talci.warehouseservice.exceptions.ItemNotFoundException;
import tech.talci.warehouseservice.model.InventoryItem;
import tech.talci.warehouseservice.repository.InventoryItemRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryItemService {

    private final InventoryItemRepository itemRepository;
    private final StreamBridge streamBridge;

    /**
     * Method is responsible for fetching all the inventory items.
     *
     * @return inventoryItemDtoList
     */
    public List<InventoryItemDto> findAll() {
        return this.itemRepository.findAll().stream()
            .map(this::mapToInventoryItemDto)
            .collect(Collectors.toList());
    }

    /**
     * Method is responsible for fetching the item by sku code. Throws an ItemNotFoundException in
     * case item was not found.
     *
     * @param skuCode
     * @return inventoryItemDto
     */
    public InventoryItemDto findBySkuCode(String skuCode) {
        InventoryItem fetchedItem =
            this.itemRepository
                .findBySkuCode(skuCode)
                .orElseThrow(
                    () ->
                        new ItemNotFoundException(
                            "Item with sku code " + skuCode + " was not found"));

        return mapToInventoryItemDto(fetchedItem);
    }

    /**
     * Method is responsible for fetching the item by ID. Throws an ItemNotFoundException in case
     * item was not found.
     *
     * @param id
     * @return inventoryItemDto
     */
    public InventoryItemDto findById(String id) {
        InventoryItem fetchedItem =
            this.itemRepository
                .findById(id)
                .orElseThrow(
                    () -> new ItemNotFoundException("Item with id " + id + " was not found"));

        return mapToInventoryItemDto(fetchedItem);
    }

    /**
     * The method is responsible for converting InventoryItemDto object into a data model,
     * persisting it and returning it converted as DTO object.
     *
     * @param inventoryItemDto
     * @return persisted InventoryItem with a generated ID
     */
    public InventoryItemDto addInventoryItem(InventoryItemDto inventoryItemDto) {
        InventoryItem inventoryItem = mapToInventoryItem(inventoryItemDto);

        InventoryItem savedItem = this.itemRepository.save(inventoryItem);

        log.debug(
            "Inventory item with ID {} and sku code {} was added",
            savedItem.getId(),
            savedItem.getSkuCode());

        this.sendStockData(savedItem);

        return mapToInventoryItemDto(savedItem);
    }

    /**
     * The method is responsible for handling PUT requests and updating the inventory accordingly.
     * Throws ItemNotFoundException when the item was not found.
     *
     * @param itemDto
     * @return inventoryItemDto
     */
    public InventoryItemDto updateInventoryItem(InventoryItemDto itemDto) {
        // Check if the item exists
        InventoryItem fetchedItem =
            this.itemRepository
                .findById(itemDto.getId())
                .orElseThrow(
                    () ->
                        new ItemNotFoundException(
                            "You were trying to update an item with ID "
                                + itemDto.getId()
                                + ". However, it was not found"));

        InventoryItem convetedItem = mapToInventoryItem(itemDto);

        InventoryItem savedItem = this.itemRepository.save(convetedItem);

        this.sendStockData(savedItem);

        return mapToInventoryItemDto(savedItem);
    }

    /**
     * Method is responsible for checking the availability of products in bulk in order to reduce
     * number of API calls. In case if the item is out of stock, it will break out of loop and
     * return response back to the consumer notifying that the one of the items is out of stock.
     *
     * @param stockValidationRequest an event that is sent by an order service before proceeding.
     * @return returns first unavailable item
     */
    public StockValidationResponse checkStockAvailability(
        StockValidationRequest stockValidationRequest) {
        var stockValidationResponse = new StockValidationResponse();

        // TODO: Optimize, maybe caching?
        for (String skuCode : stockValidationRequest.getSkuCodes()) {
            InventoryItem fetchedItem =
                this.itemRepository
                    .findBySkuCode(skuCode)
                    .orElseThrow(
                        () ->
                            new ItemNotFoundException(
                                "Item with sku code " + skuCode + " was not found"));

            if (fetchedItem.getLeftInStock() < 0) {
                stockValidationResponse.setInStock(false);
                stockValidationResponse.setUnavailableItem(skuCode);
                return stockValidationResponse;
            }
        }

        stockValidationResponse.setInStock(true);

        return stockValidationResponse;
    }

    /**
     * Method is responsible for sending updated stock data to subscribed microservices. In case the
     * item is not present the service gets notified.
     *
     * @param productAddedEvent - an event fired by the product service whenever a
     *                          product is added.
     */
    public void sendStockData(ProductAddedEvent productAddedEvent) {
        if (productAddedEvent != null && productAddedEvent.getProductDto() != null &&
            !StringUtils.isEmpty(productAddedEvent.getProductDto().getSkuCode())) {

            String skuCode = productAddedEvent.getProductDto().getSkuCode();
            Optional<InventoryItem> itemOptional = this.itemRepository.findBySkuCode(skuCode);

            StockUpdateEvent updateEvent = null;

            if (itemOptional.isPresent()) {
                InventoryItem item = itemOptional.get();

                updateEvent = new StockUpdateEvent(LocalDateTime.now(), item.getSkuCode(),
                    item.getLeftInStock(), true);
            } else {
                updateEvent = new StockUpdateEvent(LocalDateTime.now(), skuCode, -1, false);
            }

            Message<StockUpdateEvent> updateEventMessage =
                MessageBuilder.withPayload(updateEvent).build();

            this.streamBridge.send("stockUpdatedEventSupplier-out-0", updateEventMessage);
        }
    }

    public void sendStockData(InventoryItem item) {
        StockUpdateEvent updateEvent =
            new StockUpdateEvent(LocalDateTime.now(), item.getSkuCode(), item.getLeftInStock(),
                true);

        Message<StockUpdateEvent> updateEventMessage = MessageBuilder.withPayload(updateEvent)
            .build();

        this.streamBridge.send("stockUpdatedEventSupplier-out-0", updateEventMessage);
    }

    /**
     * Method is responsible for converting Java POJO to a Data Transfer Object. Does not ignore any
     * fields. Throws NullPointer in case the object is null.
     *
     * @param inventoryItem to be converted
     * @return converted DTO object
     */
    private InventoryItemDto mapToInventoryItemDto(InventoryItem inventoryItem) {
        Objects.requireNonNull(inventoryItem, "Inventory Item cannot be null when being converted");
        return InventoryItemDto.builder()
            .id(inventoryItem.getId())
            .addedOn(inventoryItem.getAddedOn())
            .lastUpdated(inventoryItem.getLastUpdated())
            .leftInStock(inventoryItem.getLeftInStock())
            .skuCode(inventoryItem.getSkuCode())
            .build();
    }

    /**
     * Utility method is responsible for converting the Data Transfer Object to a Java POJO. Ignores
     * fields like id, lastUpdated and addedOn. Throws NullPointer in case the object is null.
     *
     * @param dto to be converted
     * @return converted Java POJO.
     */
    private InventoryItem mapToInventoryItem(InventoryItemDto dto) {
        Objects.requireNonNull(dto, "Inventory Item Dto cannot be null when being converted");
        return InventoryItem.builder()
            .leftInStock(dto.getLeftInStock())
            .skuCode(dto.getSkuCode())
            .build();
    }
}
