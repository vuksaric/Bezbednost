package com.example.KT1.services.implementation;

import com.example.KT1.config.EmailContext;
import com.example.KT1.model.Subject;
import com.example.KT1.services.IEmailService;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;

@Service
public class EmailService implements IEmailService {
    private final EmailContext _emailContext;

    public EmailService(EmailContext emailContext) {
        _emailContext = emailContext;
    }

    @Override
    public void approveRegistrationMail(Subject subject) {
        String to = subject.getUser().getUsername();
        System.out.println(to);
        String title = "Your registration has been approved.";
        Context context = new Context();
        context.setVariable("name", String.format("%s %s", subject.getName(), subject.getSurname()));
        context.setVariable("link", String.format("http://localhost:4200/frontpage/login/%s", subject.getEmail()));
        _emailContext.send(to, title, "approveRegistration", context);
    }

    @Override
    public void denyRegistrationMail(Subject subject) {
        String to = subject.getUser().getUsername();
        System.out.println(to);
        String title = "Your registration has been denied.";
        Context context = new Context();
        context.setVariable("name", String.format("%s %s", subject.getName(), subject.getSurname()));
        _emailContext.send(to, title, "denyRegistration", context);
    }
}
