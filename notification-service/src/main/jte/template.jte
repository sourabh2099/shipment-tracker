@import com.shipment.track.notification_service.dto.NotificationMessage
@import com.shipment.track.notification_service.dto.enums.MessageType.MessageType
@param NotificationMessage  notificationMessage

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Email Template</title>
</head>
<body>
<div>
    @if(notificationMessage.getMessageType().equals(MessageType.TRANSACTIONAL))
        <div>This message is of type transactional</div>
    @else
        <div>this is other type of message {notificationMessage.getMessageType()}</div>
    @endif
    This is the first div
</div>
</body>
</html>