package tech.talci.warehouseservice.events;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class StockUpdateEvent extends AbstractEvent {

    private final String skuCode;
    private final Integer count;
    private final boolean isPresent;

    public StockUpdateEvent(LocalDateTime timeStamp, String skuCode, Integer count,
        boolean isPresent) {

        super(timeStamp);
        this.skuCode = skuCode;
        this.count = count;
        this.isPresent = isPresent;
    }
}
