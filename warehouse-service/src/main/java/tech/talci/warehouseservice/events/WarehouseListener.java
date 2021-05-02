package tech.talci.warehouseservice.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.talci.warehouseservice.service.InventoryItemService;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class WarehouseListener {

    private final InventoryItemService itemService;

    @Bean
    public Consumer<String> productAddedEventSupplier() {
        return this.itemService::sendStockData;
    }
}
