package com.shipment.track.shipment_tracker.events;

import com.shipment.track.shipment_tracker.model.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateShipmentsForUserEvent extends ApplicationEvent {
    private final User user;
    public CreateShipmentsForUserEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

}
