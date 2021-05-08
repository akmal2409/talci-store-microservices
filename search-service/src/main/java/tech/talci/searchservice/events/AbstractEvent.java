package tech.talci.searchservice.events;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public abstract class AbstractEvent {
    private final LocalDateTime timeStamp;

    protected AbstractEvent(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
