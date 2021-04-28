package com.example.KT1.services.implementation;

import com.example.KT1.dto.request.ChangePasswordRequest;
import com.example.KT1.dto.request.ForgotPasswordRequest;
import com.example.KT1.dto.request.LoginRequest;
import com.example.KT1.dto.request.RegistrationRequest;
import com.example.KT1.dto.response.UserResponse;
import com.example.KT1.model.Authority;
import com.example.KT1.model.PasswordToken;
import com.example.KT1.model.Subject;
import com.example.KT1.model.User;
import com.example.KT1.repository.AuthorityRepository;
import com.example.KT1.repository.PasswordTokenRepository;
import com.example.KT1.repository.SubjectRepository;
import com.example.KT1.repository.UserRepository;
import com.example.KT1.security.Token;
import com.example.KT1.services.IAuthService;
import com.example.KT1.services.IEmailService;
import com.example.KT1.util.GeneralException;
import com.example.KT1.util.enums.RequestStatus;
import com.example.KT1.util.enums.UserRoles;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class AuthService implements IAuthService {

    private final PasswordEncoder _passwordEncoder;
    private final UserRepository _userRepository;
    private final AuthorityRepository _authorityRepository;
    private final SubjectRepository _subjectRepository;
    private final Token token;
    private final IEmailService _emailService;
    private final PasswordTokenRepository _ptRepository;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthorityRepository authorityRepository, SubjectRepository subjectRepository, Token token, IEmailService emailService, PasswordTokenRepository ptRepository) {
        _passwordEncoder = passwordEncoder;
        _userRepository = userRepository;
        _authorityRepository = authorityRepository;
        _subjectRepository = subjectRepository;
        this.token = token;
        _emailService = emailService;
        _ptRepository = ptRepository;
    }


    @Override
    public UserResponse login(LoginRequest request) {

        User user = _userRepository.findOneByUsername(request.getUsername());
        if(user == null || !_passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new GeneralException("Bad credentials.", HttpStatus.BAD_REQUEST);
        }
        if(user.getSubject() != null && user.getSubject().getRequestStatus().equals(RequestStatus.PENDING)){
            throw new GeneralException("Your registration hasn't been approved yet.", HttpStatus.BAD_REQUEST);
        }
        if(user.getSubject() != null && user.getSubject().getRequestStatus().equals(RequestStatus.DENIED)){
            throw new GeneralException("Your registration has been denied.", HttpStatus.BAD_REQUEST);
        }
        if(user.getSubject() != null && user.getSubject().getRequestStatus().equals(RequestStatus.APPROVED)){
            throw new GeneralException("Your registration has been approved by admin. Please activate your account.", HttpStatus.BAD_REQUEST);
        }

        String jwt = "";
        int expiresIn = 0;
        jwt = token.generateToken(request.getUsername());
        expiresIn = token.getEXPIRES_IN();

        UserResponse userResponse = mapUserToUserResponse(user);
        userResponse.setToken(jwt);
        userResponse.setTokenExpiresIn(expiresIn);
        return userResponse;
    }

    @Override
    public UserResponse registerSubject(RegistrationRequest request){
        if(!request.getPassword().equals(request.getRePassword())){
            throw new GeneralException("Passwords do not match.", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        Subject subject = new Subject();
        user.setUsername(request.getUsername());
        user.setPassword(_passwordEncoder.encode(request.getPassword()));
        user.setUserRole(UserRoles.USER);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(_authorityRepository.findOneByName("ROLE_CUSTOMER"));
        user.setAuthorities(new HashSet<>(authorities));
        subject.setName(request.getFirstName());
        subject.setSurname(request.getLastName());
        subject.setOrganisation(request.getOrganisation());
        subject.setOrganisationUnit(request.getOrganisationUnit());
        subject.setRequestStatus(RequestStatus.PENDING);
        subject.setEmail(request.getUsername());

        Subject savedSubject = _subjectRepository.save(subject);
        savedSubject.setUser(user);
        user.setSubject(savedSubject);
        User savedUser = _userRepository.save(user);

        return mapUserToUserResponse(savedUser);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        _emailService.forgotPassword(request);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        if(!request.getPassword().equals(request.getRePassword())){
            throw new GeneralException("Passwords do not match.", HttpStatus.BAD_REQUEST);
        }
        PasswordToken passwordToken = _ptRepository.findOneByToken(request.getEmail());
        System.out.println(request.getEmail());
        System.out.println(passwordToken.getEmail());
        User user = _userRepository.findOneByUsername(passwordToken.getEmail());
        user.setPassword(_passwordEncoder.encode(request.getPassword()));
        _userRepository.save(user);
        _ptRepository.delete(passwordToken);
    }


    private UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setUserRoles(user.getUserRole().toString());
        return userResponse;
    }
}
