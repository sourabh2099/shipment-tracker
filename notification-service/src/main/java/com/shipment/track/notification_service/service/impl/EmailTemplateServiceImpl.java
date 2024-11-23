package com.shipment.track.notification_service.service.impl;

import com.shipment.track.notification_service.dto.NotificationMessage;
import com.shipment.track.notification_service.service.EmailTemplateService;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {
    private final TemplateEngine templateEngine;

    public EmailTemplateServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String generateEmailTemplate(NotificationMessage notificationMessage) {
        TemplateOutput templateOutput = new StringOutput();
        templateEngine.render("template.jte",notificationMessage,templateOutput);
        System.out.println(templateOutput);
        return "";
    }
}
