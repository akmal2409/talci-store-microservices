package tech.talci.searchservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@ToString
public class Promotion {

    @Id
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
        GLOBAL_SCOPED_SALE("Seasonal Sale"),
        PRODUCT_SCOPED_SALE("Product On Sale"),
        CATEGORY_SCOPED_SALE("Category Sale");

        final String type;

        PromotionType(String type) {
            this.type = type;
        }
    }
}
