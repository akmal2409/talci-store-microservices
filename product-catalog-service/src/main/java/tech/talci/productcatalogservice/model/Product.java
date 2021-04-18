package tech.talci.productcatalogservice.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(value = "product")
@Getter @Setter @ToString(exclude = {"categories", "promotions", "productImages"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    public enum Status { AVAILABLE, OUT_OF_STOCK, PENDING }

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
    private Boolean onSale;
    private Status status;

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
    private List<Promotion> promotions = new ArrayList<>();
    private List<ProductImage> productImages = new ArrayList<>();
}
