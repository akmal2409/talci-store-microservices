package tech.talci.warehouseservice.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class StockValidationRequest {
    private List<String> skuCodes = new ArrayList<>();
}
