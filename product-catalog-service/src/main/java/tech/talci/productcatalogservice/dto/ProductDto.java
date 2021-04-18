package tech.talci.productcatalogservice.dto;

import lombok.*;
import tech.talci.productcatalogservice.model.Product;
import tech.talci.productcatalogservice.model.ProductImage;
import tech.talci.productcatalogservice.model.Promotion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private BigDecimal pricePerUnit;
    private String countryOfOrigin;
    private String producer;
    private Integer leftInStock;
    private BigDecimal shippingCostFrom;
    private Boolean onSale;
    private Double rating;
    private Long orderCount;
    private LocalDateTime addedOn;
    private LocalDateTime lastUpdated;
    private Product.Status status;
    private List<CategoryDto> categories = new ArrayList<>();
    private List<Promotion> promotions = new ArrayList<>();
    private List<ProductImage> productImages = new ArrayList<>();
}
