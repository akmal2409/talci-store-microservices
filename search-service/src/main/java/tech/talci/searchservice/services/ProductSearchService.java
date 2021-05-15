package tech.talci.searchservice.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.talci.searchservice.events.ProductAddedEvent;
import tech.talci.searchservice.events.StockUpdateEvent;
import tech.talci.searchservice.model.Product;
import tech.talci.searchservice.repositories.ProductRepository;
import tech.talci.searchservice.repositories.SpringDataProductRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSearchService {

    private final ProductRepository productRepository;
    private final SpringDataProductRepository springDataProductRepository;

    public void addProduct(ProductAddedEvent productAddedEvent) {
        if (productAddedEvent != null && productAddedEvent.getProduct() != null) {
            Product product = productAddedEvent.getProduct();
            log.debug("Received product from product service with ID " + product.getId());
            this.springDataProductRepository.save(product);
        }
    }

    public void updateStock(StockUpdateEvent stockUpdateEvent) {
        if (stockUpdateEvent != null && !StringUtils.isEmpty(stockUpdateEvent.getSkuCode())) {
            Optional<Product> productOptional =
                this.springDataProductRepository.findBySkuCode(stockUpdateEvent.getSkuCode());

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

                this.springDataProductRepository.save(product);
            }
        }
    }
}
