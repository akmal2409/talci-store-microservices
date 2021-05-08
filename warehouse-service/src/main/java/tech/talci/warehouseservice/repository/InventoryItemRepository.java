package tech.talci.warehouseservice.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import tech.talci.warehouseservice.model.InventoryItem;

public interface InventoryItemRepository extends MongoRepository<InventoryItem, String> {

    Optional<InventoryItem> findBySkuCode(String skuCode);
}
