package tech.talci.productcatalogservice.events;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.talci.productcatalogservice.service.ProductService;

@Configuration
@RequiredArgsConstructor
public class ProductListener {

    private final ProductService productService;

    @Bean
    public Consumer<StockUpdateEvent> stockUpdatedEventSupplier() {
        return this.productService::updateStockQuantity;
    }
}
