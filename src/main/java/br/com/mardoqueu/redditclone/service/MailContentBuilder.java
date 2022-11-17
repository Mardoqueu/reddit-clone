package br.com.mardoqueu.redditclone.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;

/*
This class contains the logic to create our email message using the HTML template we are going to provide.
*/
@Service
@AllArgsConstructor
public class MailContentBuilder {
    private final TemplateEngine templateEngine;

    //We are injecting the email message into the HTML template by setting the message into the Context of the TemplateEngine.
    String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }

}
