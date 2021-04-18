package tech.talci.warehouseservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
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
