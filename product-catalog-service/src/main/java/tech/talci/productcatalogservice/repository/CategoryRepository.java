package tech.talci.productcatalogservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tech.talci.productcatalogservice.model.Category;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findAllByName(String name);
}
