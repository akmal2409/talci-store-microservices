package tech.talci.productcatalogservice.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import tech.talci.productcatalogservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findBySkuCode(String skuCode);
}
