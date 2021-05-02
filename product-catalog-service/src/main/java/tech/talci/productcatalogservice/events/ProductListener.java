package tech.talci.productcatalogservice.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.talci.productcatalogservice.service.ProductService;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class ProductListener {

    private final ProductService productService;

    @Bean
    public Consumer<StockUpdateEvent> stockUpdatedEventSupplier() {
        return this.productService::updateStockQuantity;
    }
}
