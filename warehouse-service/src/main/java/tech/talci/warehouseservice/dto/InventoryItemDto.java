package tech.talci.warehouseservice.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryItemDto {

    private String id;
    private String skuCode;
    private Integer leftInStock;
    private LocalDateTime addedOn;
    private LocalDateTime lastUpdated;
}
