package com.shipment.track.notification_service.dto.enums.MessageType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

// when scaled this should belong to the database to allow updates
public enum MessageType {
    PROMOTIONAL,
    INFORMATION,
    TRANSACTIONAL;
    private static final HashMap<Integer, MessageType> messageTypeList = new HashMap<>();

    static {
        for (MessageType msg : EnumSet.allOf(MessageType.class)) {
            messageTypeList.put(msg.ordinal(), msg);
        }
    }

    public static HashMap<Integer, MessageType> getMessageTypeList() {
        return messageTypeList;
    }
}
