package tech.talci.searchservice.repositories;

import java.util.List;
//import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import java.util.Optional;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import tech.talci.searchservice.model.Product;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(String id);

    Optional<Product> findBySkuCode(String skuCode);

    Long count();

    List<IndexedObjectInformation> saveAll(List<Product> products);

    String save(Product product);
}
