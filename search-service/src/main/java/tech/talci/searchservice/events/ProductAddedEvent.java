package tech.talci.searchservice.events;

import java.time.LocalDateTime;
import lombok.Getter;
import tech.talci.searchservice.dto.ProductDto;

@Getter
public class ProductAddedEvent extends AbstractEvent {
    private final ProductDto productDto;

    public ProductAddedEvent(ProductDto productDto, LocalDateTime timeStamp) {
        super(timeStamp);
        this.productDto = productDto;
    }
}
