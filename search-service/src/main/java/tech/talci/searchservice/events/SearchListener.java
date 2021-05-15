package tech.talci.searchservice.events;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.talci.searchservice.services.ProductSearchService;

@Configuration
@RequiredArgsConstructor
public class SearchListener {
    private final ProductSearchService productSearchService;


    @Bean
    public Consumer<ProductAddedEvent> productAddedEventSupplier() {
        return this.productSearchService::addProduct;
    }

    @Bean
    public Consumer<StockUpdateEvent> stockUpdatedEventSupplier() {
        return this.productSearchService::updateStock;
    }
}
