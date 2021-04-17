package tech.talci.productcatalogservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.talci.productcatalogservice.model.Product;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findBySkuCode(String skuCode);
}
