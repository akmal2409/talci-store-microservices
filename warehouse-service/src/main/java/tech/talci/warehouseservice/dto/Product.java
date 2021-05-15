package tech.talci.productcatalogservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "product")
@Getter
@Setter
@ToString(exclude = {"categories", "promotions", "productImages"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    private String id;

    private String name;
    private String description;
    private BigDecimal pricePerUnit;
    private String skuCode;
    private String countryOfOrigin;
    private String producer;
    private Integer leftInStock;
    private BigDecimal shippingCostFrom;
    private boolean available;

    @DecimalMax(value = "5.0", message = "Rating cannot be greater than 5.0")
    @DecimalMin(value = "1.0", message = "Rating cannot be smaller than 1.0")
    private Double rating;

    private Long orderCount;

    @CreatedDate
    private LocalDateTime addedOn;
    @LastModifiedDate
    private LocalDateTime lastUpdated;

    @DBRef(lazy = true)
    private List<Category> categories = new ArrayList<>();

    @DBRef(lazy = true)
    private List<Promotion> promotions = new ArrayList<>();

    private List<ProductImage> productImages = new ArrayList<>();
    private Map<String, List<String>> specs = new HashMap<>();
}
