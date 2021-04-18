package tech.talci.warehouseservice.dto;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class StockValidationResponse {
    private boolean inStock = false;
    // Sku Code of an unavailable item
    private String unavailableItem;
}
