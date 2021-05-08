package tech.talci.warehouseservice.events;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.talci.warehouseservice.service.InventoryItemService;

@Configuration
@RequiredArgsConstructor
public class WarehouseListener {

    private final InventoryItemService itemService;

    @Bean
    public Consumer<ProductAddedEvent> productAddedEventSupplier() {
        return this.itemService::sendStockData;
    }
}
