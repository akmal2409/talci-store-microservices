package tech.talci.productcatalogservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.talci.productcatalogservice.dto.ProductDto;
import tech.talci.productcatalogservice.dto.mapper.ProductMapper;
import tech.talci.productcatalogservice.events.ProductAddedEvent;
import tech.talci.productcatalogservice.events.StockUpdateEvent;
import tech.talci.productcatalogservice.model.Product;
import tech.talci.productcatalogservice.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final StreamBridge streamBridge;

    public List<ProductDto> findAll() {
        return this.productRepository.findAll().stream()
            .map(this.productMapper::mapToDto)
            .collect(Collectors.toList());
    }

    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.mapToProduct(productDto);

        // until we get a confirmation from the warehouse service
        // that the product is available.
        product.setAvailable(false);
        product = productRepository.save(product);

        ProductAddedEvent addedEvent = new ProductAddedEvent(
            this.productMapper.mapToDto(product),
            LocalDateTime.now()
        );
        // notify services that rely on the data
        this.streamBridge.send("productAddedEventSupplier-out-0", addedEvent);

        log.debug("Product with ID: {} SKU Code: {} was added", product.getId(),
            product.getSkuCode());

        return productMapper.mapToDto(product);
    }

    public void updateStockQuantity(StockUpdateEvent stockUpdateEvent) {
        if (stockUpdateEvent != null && !StringUtils.isEmpty(stockUpdateEvent.getSkuCode())) {
            Optional<Product> productOptional =
                this.productRepository.findBySkuCode(stockUpdateEvent.getSkuCode());

            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                if (product.getLastUpdated().compareTo(stockUpdateEvent.getTimeStamp()) != -1) {
                    // Meaning that the product has already been updated
                    return;
                }

                if (stockUpdateEvent.isPresent() && stockUpdateEvent.getCount() > 0) {
                    product.setLeftInStock(stockUpdateEvent.getCount());
                    product.setAvailable(true);
                } else {
                    product.setLeftInStock(stockUpdateEvent.getCount());
                    product.setAvailable(false);
                }

                log.debug(
                    "Stock was updated by Warehouse Service for a product with ID {} "
                        + "SKU Code: {} with Count: {} Present: {}",
                    product.getId(),
                    product.getSkuCode(),
                    stockUpdateEvent.getCount(),
                    stockUpdateEvent.isPresent());

                this.productRepository.save(product);
            }
        }
    }
}
