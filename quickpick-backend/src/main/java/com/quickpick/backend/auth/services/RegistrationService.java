package com.quickpick.backend.auth.services;

import com.quickpick.backend.auth.dto.RegistrationRequest;
import com.quickpick.backend.auth.dto.RegistrationResponse;
import com.quickpick.backend.auth.entities.User;
import com.quickpick.backend.auth.helper.VerificationCodeGenerator;
import com.quickpick.backend.auth.repositories.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

@Service
public class RegistrationService {
    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private VerificationCodeGenerator verificationCodeGenerator;
    @Autowired
    private EmailService emailService;

    public RegistrationResponse createUser(RegistrationRequest registrationRequest) {
        User confirmUser = userDetailRepository.findByEmail(registrationRequest.getEmail());

        if (confirmUser != null) {
//            String message = "Email already exists. Please try again.";
//            return new RegistrationResponse(400, message);
            RegistrationResponse message = RegistrationResponse.builder()
                    .httpCode(404)
                    .message("Email already exists!")
                    .build();

            return message;
        }

        try {
            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setFirstName(registrationRequest.getFirstName());
            user.setLastName(registrationRequest.getLastName());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setPhoneNumber(registrationRequest.getPhoneNumber());
            user.setProvider("manual");
            user.setEnabled(true);

            String code = verificationCodeGenerator.generateToken();

            user.setVerificationCode(code);
            user.setAuthorities(authorityService.getUserAuthority());

            userDetailRepository.save(user);
            emailService.sendMail(user);

            return RegistrationResponse.builder()
                    .httpCode(200)
                    .message("User account created successfully")
                    .build();

        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServerErrorException(e.getMessage(),e.getCause());
        }
    }

    public RegistrationResponse verifyUser(String email) {
        User user = userDetailRepository.findByEmail(email);
        if (user == null) {
            return RegistrationResponse.builder()
                    .httpCode(400)
                    .message("User doesn't exist")
                    .build();
        }

        user.setEnabled(true);
        userDetailRepository.save(user);

        return RegistrationResponse.builder()
                .httpCode(200)
                .message("User account successfully verified!")
                .build();
    }
}
