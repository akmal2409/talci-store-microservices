package tech.talci.productcatalogservice.events;

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductAddedEvent {
    private String skuCode;
}
