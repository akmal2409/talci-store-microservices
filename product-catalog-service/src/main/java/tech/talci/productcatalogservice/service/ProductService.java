package tech.talci.productcatalogservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import tech.talci.productcatalogservice.dto.ProductDto;
import tech.talci.productcatalogservice.dto.mapper.ProductMapper;
import tech.talci.productcatalogservice.events.StockUpdateEvent;
import tech.talci.productcatalogservice.model.Product;
import tech.talci.productcatalogservice.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final StreamBridge streamBridge;

    public List<ProductDto> findAll() {
        return this.productRepository
                .findAll()
                .stream()
                .map(this.productMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.mapToProduct(productDto);

        // until we get a confirmation from the warehouse service
        // that the product is available.
        product.setStatus(Product.Status.OUT_OF_STOCK);
        product = productRepository.save(product);

        // notify warehouse service
        this.streamBridge
                .send("productAddedEventSupplier-out-0", product.getSkuCode());
        log.debug("Product with ID: {} SKU Code: {} was added",
                product.getId(), product.getSkuCode());

        return productMapper.mapToDto(product);
    }

    public void updateStockQuantity(StockUpdateEvent stockUpdateEvent) {
        Optional<Product> productOptional =
                this.productRepository.findBySkuCode(stockUpdateEvent.getSkuCode());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            if (stockUpdateEvent.isPresent() && stockUpdateEvent.getCount() > 0) {
                product.setStatus(Product.Status.IN_STOCK);
                product.setLeftInStock(stockUpdateEvent.getCount());
                product.setAvailable(true);
            } else {
                product.setStatus(Product.Status.OUT_OF_STOCK);
                product.setLeftInStock(stockUpdateEvent.getCount());
                product.setAvailable(false);
            }

            log.debug("Stock was updated by Warehouse Service for a product with ID {} " +
                    "SKU Code: {} with Count: {} Present: {}", product.getId(), product.getSkuCode(),
                    stockUpdateEvent.getCount(), stockUpdateEvent.isPresent());

            this.productRepository.save(product);
        }
    }
}
