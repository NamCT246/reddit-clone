package com.namct.reddit.mail;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

// Build the template with Thymeleaf template engine

@Service
@AllArgsConstructor
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    public String build(String message, String templateName) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process(templateName, context);
    }
}