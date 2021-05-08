package tech.talci.searchservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tech.talci.searchservice.model.ProductImage;
import tech.talci.searchservice.model.Promotion;

@Getter
@Setter
@ToString
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
    private String skuCode;
    private Boolean onSale;
    private Double rating;
    private Long orderCount;
    private LocalDateTime addedOn;
    private LocalDateTime lastUpdated;
    private List<CategoryDto> categories = new ArrayList<>();
    private List<Promotion> promotions = new ArrayList<>();
    private List<ProductImage> productImages = new ArrayList<>();
    private Map<String, List<String>> specs = new HashMap<>();
}
