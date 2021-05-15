package tech.talci.searchservice.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import tech.talci.searchservice.model.Product;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private static final String PRODUCT_INDEX = "product_index";

    private final ElasticsearchOperations elasticsearchOperations;
    private final RestHighLevelClient restHighLevelClient;

    @Override
    public List<Product> findAll() {
        var queryBuilder =
            QueryBuilders
                .matchAllQuery();

        var query = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .build();

        var searchHits = this.elasticsearchOperations
            .search(query, Product.class, IndexCoordinates.of(PRODUCT_INDEX));

        List<Product> products = new ArrayList<>();

        searchHits.stream()
            .forEach(productSearchHit -> products.add(productSearchHit.getContent()));

        return products;
    }

    @Override
    public Optional<Product> findById(String id) {
        var queryBuilder =
            QueryBuilders
                .termQuery("_id", id);

        var query = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .build();

        var searchHit = this.elasticsearchOperations
            .searchOne(query, Product.class, IndexCoordinates.of(PRODUCT_INDEX));

        return Optional.of(searchHit.getContent());
    }

    @Override
    public Optional<Product> findBySkuCode(final String skuCode) {
        var queryBuilder =
            QueryBuilders
                .queryStringQuery(skuCode)
                .field("sku_code")
                .defaultOperator(Operator.AND);

        var searchQuery = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .build();

        var productSearchHit =
            elasticsearchOperations
                .searchOne(searchQuery, Product.class, IndexCoordinates.of(PRODUCT_INDEX));

        return Optional.of(productSearchHit.getContent());
    }

    @SneakyThrows
    @Override
    public Long count() {
        var countRequest = new CountRequest(PRODUCT_INDEX)
            .routing("routing")
            .indicesOptions(IndicesOptions.lenientExpandOpen())
            .preference("_local");

        var searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        countRequest.source(searchSourceBuilder);

        var countResponse = restHighLevelClient
            .count(countRequest, RequestOptions.DEFAULT);

        return countResponse.getCount();
    }

    @Override
    public List<IndexedObjectInformation> saveAll(final List<Product> products) {
        // Create bulk index
        List<IndexQuery> queries = products.stream()
            .map(product ->
                new IndexQueryBuilder()
                    .withId(product.getId().toString())
                    .withObject(product).build())
            .collect(Collectors.toList());

        return elasticsearchOperations
            .bulkIndex(queries, IndexCoordinates.of(PRODUCT_INDEX));
    }

    @Override
    public String save(final Product product) {
        IndexQuery query = new IndexQueryBuilder()
            .withId(product.getId())
            .withObject(product)
            .build();

        return elasticsearchOperations
            .index(query, IndexCoordinates.of(PRODUCT_INDEX));
    }
}
