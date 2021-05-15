package tech.talci.searchservice.events;

import java.time.LocalDateTime;
import lombok.Getter;
import tech.talci.searchservice.model.Product;

@Getter
public class ProductAddedEvent extends AbstractEvent {
    private final Product product;

    public ProductAddedEvent(Product product, LocalDateTime timeStamp) {
        super(timeStamp);
        this.product = product;
    }
}
