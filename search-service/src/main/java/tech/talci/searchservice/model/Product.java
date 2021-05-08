package tech.talci.searchservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@ToString
@Document(indexName = "productIndex", shards = 1)
public class Product {

    @Id
    private String id;

    @Field(name = "name", type = FieldType.Text)
    private String name;

    @Field(name = "description", type = FieldType.Text)
    private String description;

    @Field(name = "price_per_unit", type = FieldType.Double)
    private BigDecimal pricePerUnit;

    @Field(name = "country_of_origin", type = FieldType.Keyword)
    private String countryOfOrigin;

    @Field(name = "producer", type = FieldType.Keyword)
    private String producer;

    @Field(name = "left_in_stock", type = FieldType.Integer)
    private Integer leftInStock;

    @Field(name = "shipping_cost_from", type = FieldType.Double)
    private BigDecimal shippingCostFrom;

    @Field(name = "available", type = FieldType.Boolean)
    private boolean available;

    @Field(name = "rating", type = FieldType.Double)
    private Double rating;

    @Field(name = "order_count", type = FieldType.Long)
    private Long orderCount;

    @Field(name = "added_on", type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime addedOn;

    @Field(name = "last_updated", type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime lastUpdated;

    @Field(type = FieldType.Nested, name = "categories")
    private List<Category> categories = new ArrayList<>();

    @Field(type = FieldType.Nested, name = "promotions")
    private List<Promotion> promotions = new ArrayList<>();

    @Field(type = FieldType.Nested, name = "product_images")
    private List<ProductImage> productImages = new ArrayList<>();

    @Field(type = FieldType.Object, name = "specs")
    private Map<String, List<String>> specs = new HashMap<>();
}
