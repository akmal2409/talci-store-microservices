package tech.talci.productcatalogservice.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Promotion {

    public enum PromotionType {
        GLOBAL_SCOPED_SALE, PRODUCT_SCOPED_SALE, CATEGORY_SCOPED_SALE
    }

    private Long id;
    private String name;
    private String description;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private BigDecimal minPurchaseValue;
    private Integer minProductAmount;
    private Integer discount;
    private PromotionType type;
}
