package tech.talci.productcatalogservice.events;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StockUpdateEvent {
    private String skuCode;
    private Integer count;
    private boolean isPresent;
}
