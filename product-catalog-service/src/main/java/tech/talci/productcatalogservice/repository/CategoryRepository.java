package tech.talci.productcatalogservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tech.talci.productcatalogservice.model.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Page<Category> findAllByName(String name);
}
