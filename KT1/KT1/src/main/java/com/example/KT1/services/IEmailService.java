package com.example.KT1.services;

import com.example.KT1.model.Subject;

public interface IEmailService {
    void approveRegistrationMail(Subject subject);
    void denyRegistrationMail(Subject subject);
}
