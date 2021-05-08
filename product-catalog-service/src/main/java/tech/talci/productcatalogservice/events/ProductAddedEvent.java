package tech.talci.productcatalogservice.events;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import tech.talci.productcatalogservice.dto.ProductDto;

@Getter
public class ProductAddedEvent extends AbstractEvent {
    private final ProductDto productDto;

    public ProductAddedEvent(ProductDto productDto, LocalDateTime timeStamp) {
        super(timeStamp);
        this.productDto = productDto;
    }
}
