package tech.talci.warehouseservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StockValidationResponse {

    private boolean inStock = false;
    // Sku Code of an unavailable item
    private String unavailableItem;
}
