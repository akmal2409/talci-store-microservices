package tech.talci.searchservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Promotion {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private BigDecimal minPurchaseValue;
    private Integer minProductAmount;
    private Integer discount;
    private PromotionType type;
    public enum PromotionType {
        GLOBAL_SCOPED_SALE, PRODUCT_SCOPED_SALE, CATEGORY_SCOPED_SALE
    }
}
