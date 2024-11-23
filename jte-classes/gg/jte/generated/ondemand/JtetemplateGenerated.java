package gg.jte.generated.ondemand;
import com.shipment.track.notification_service.dto.NotificationMessage;
import com.shipment.track.notification_service.dto.enums.MessageType.MessageType;
@SuppressWarnings("unchecked")
public final class JtetemplateGenerated {
	public static final String JTE_NAME = "template.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,2,12,12,12,14,14,16,16,20,20,20,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, NotificationMessage notificationMessage) {
		jteOutput.writeContent("\r\n<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>Email Template</title>\r\n</head>\r\n<body>\r\n<div>\r\n    ");
		if (notificationMessage.getMessageType().equals(MessageType.TRANSACTIONAL)) {
			jteOutput.writeContent("\r\n        <div>This message is of type transactional</div>\r\n    ");
		} else {
			jteOutput.writeContent("\r\n        <div>this is other type of message {notificationMessage.getMessageType()}</div>\r\n    ");
		}
		jteOutput.writeContent("\r\n    This is the first div\r\n</div>\r\n</body>\r\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		NotificationMessage notificationMessage = (NotificationMessage)params.get("notificationMessage");
		render(jteOutput, jteHtmlInterceptor, notificationMessage);
	}
}
