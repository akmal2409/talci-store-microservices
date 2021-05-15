package tech.talci.searchservice.repositories;

import java.util.Optional;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import tech.talci.searchservice.model.Product;

public interface SpringDataProductRepository extends ElasticsearchRepository<Product, String> {

    @Query("{\"term\": {\"sku_code\": \"?0\"}}")
    Optional<Product> findBySkuCode(String skuCode);
}
