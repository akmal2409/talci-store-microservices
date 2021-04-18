package tech.talci.warehouseservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.talci.warehouseservice.dto.InventoryItemDto;
import tech.talci.warehouseservice.dto.StockValidationRequest;
import tech.talci.warehouseservice.dto.StockValidationResponse;
import tech.talci.warehouseservice.service.InventoryItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(InventoryItemController.BASE_URL)
@RequiredArgsConstructor
public class InventoryItemController {
    public static final String BASE_URL = "/api/inventory";
    private final InventoryItemService inventoryItemService;

    @GetMapping
    public List<InventoryItemDto> findAll() {
        return this.inventoryItemService.findAll();
    }

    @PostMapping
    public InventoryItemDto addItem(@RequestBody @Valid InventoryItemDto itemDto) {
        return this.inventoryItemService.addInventoryItem(itemDto);
    }

    @PutMapping
    public InventoryItemDto updateItem(@RequestBody @Valid InventoryItemDto itemDto) {
        return this.inventoryItemService.updateInventoryItem(itemDto);
    }

    @GetMapping("/{skuCode}")
    public InventoryItemDto findBySkuCode(@PathVariable String skuCode) {
        return this.inventoryItemService.findBySkuCode(skuCode);
    }

    @GetMapping("/{id}")
    public InventoryItemDto findById(@PathVariable String id) {
        return this.inventoryItemService.findById(id);
    }

    // bulk validation
    @PostMapping("/bulk-validation")
    public StockValidationResponse validateStockItems(@RequestBody @Valid StockValidationRequest validationRequest) {
        return this.inventoryItemService.checkStockAvailability(validationRequest);
    }
}
