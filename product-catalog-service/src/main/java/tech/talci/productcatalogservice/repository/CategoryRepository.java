package tech.talci.productcatalogservice.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import tech.talci.productcatalogservice.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {

    List<Category> findAllByName(String name);
}
