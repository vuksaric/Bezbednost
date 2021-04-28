package com.example.KT1.services.implementation;

import com.example.KT1.config.EmailContext;
import com.example.KT1.dto.request.ForgotPasswordRequest;
import com.example.KT1.model.PasswordToken;
import com.example.KT1.model.Subject;
import com.example.KT1.model.User;
import com.example.KT1.model.VerificationToken;
import com.example.KT1.repository.PasswordTokenRepository;
import com.example.KT1.repository.SubjectRepository;
import com.example.KT1.repository.UserRepository;
import com.example.KT1.repository.VerificationTokenRepository;
import com.example.KT1.services.IEmailService;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;

import java.util.Date;

@Service
public class EmailService implements IEmailService {
    private final EmailContext _emailContext;
    private final SubjectRepository _subjectRepository;
    private final UserRepository _userRepository;
    private final VerificationTokenRepository _vtRepository;
    private final PasswordTokenService _ptService;
    private final PasswordTokenRepository _ptRepository;

    public EmailService(EmailContext emailContext, SubjectRepository subjectRepository, UserRepository userRepository, VerificationTokenRepository vtRepository, PasswordTokenService ptService, PasswordTokenRepository ptRepository) {
        _emailContext = emailContext;
        _subjectRepository = subjectRepository;
        _userRepository = userRepository;
        _vtRepository = vtRepository;
        _ptService = ptService;
        _ptRepository = ptRepository;
    }

    @Override
    public void approveRegistrationMail(Subject subject) {
        String to = subject.getUser().getUsername();
        System.out.println(to);
        String title = "Your registration has been approved.";
        VerificationToken token = _vtRepository.findOneByEmail(subject.getEmail());

        Context context = new Context();
        context.setVariable("name", String.format("%s %s", subject.getName(), subject.getSurname()));
        context.setVariable("link", String.format("https://localhost:4200/frontpage/login/%s", token.getToken()));
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

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        String to = request.getEmail();
        User user = _userRepository.findOneByUsername(request.getEmail());
        System.out.println(to);
        System.out.println(user.getId());
        Subject subject = user.getSubject();
        Date now = new Date();
        PasswordToken passwordToken = _ptRepository.findOneByEmail(request.getEmail());
        if(passwordToken == null){
            passwordToken = _ptService.createToken(request.getEmail());
        }else{
            if(passwordToken.getExpiryDate().before(now)){
                _ptRepository.delete(passwordToken);
                passwordToken = _ptService.createToken(request.getEmail());
            }
        }

        String title = "Change your password";
        Context context = new Context();
        context.setVariable("name", String.format("%s %s", subject.getName(), subject.getSurname()));
        context.setVariable("link", String.format("https://localhost:4200/frontpage/change-password/%s", passwordToken.getToken()));
        _emailContext.send(to, title, "forgotPassword", context);
    }
}
