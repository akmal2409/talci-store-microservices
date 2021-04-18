package tech.talci.warehouseservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.talci.warehouseservice.model.InventoryItem;

import java.util.Optional;

public interface InventoryItemRepository extends MongoRepository<InventoryItem, String> {

    Optional<InventoryItem> findBySkuCode(String skuCode);
}
