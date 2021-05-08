package tech.talci.warehouseservice.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(value = "inventoryItem")
@Builder
public class InventoryItem {

    @Id
    private String id;
    private String skuCode;
    private Integer leftInStock;
    // TODO: Warehouse object with location, phone number

    @CreatedDate
    private LocalDateTime addedOn;

    @LastModifiedDate
    private LocalDateTime lastUpdated;
}
