package com.namct.reddit.auth.service;

import com.namct.reddit.auth.dto.Login;
import com.namct.reddit.auth.dto.Register;
import com.namct.reddit.auth.token.verification.VerificationToken;
import com.namct.reddit.auth.token.verification.VerificationTokenRepository;
import com.namct.reddit.exceptions.BaseException;
import com.namct.reddit.users.UserModel;
import com.namct.reddit.users.UserRepository;
import com.namct.reddit.mail.MailContentBuilder;
import com.namct.reddit.mail.model.NotificationEmail;
import com.namct.reddit.mail.service.MailService;

import static com.namct.reddit.mail.constants.ActivationURI.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import static java.time.Instant.now;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegisterService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepository verificationTokenRepository;
    private MailContentBuilder mailContentBuilder;
    private MailService mailService;

    @Transactional
    public void signup(Register register) {
        // if(isUserExists(register.getEmail(), register.getUsername())) {
        // return;
        // };

        UserModel user = new UserModel();

        user.setUsername(register.getUsername());
        user.setEmail(register.getEmail());
        user.setPassword(encodePassword(register.getPassword()));
        user.setCreated(now());

        userRepository.save(user);

        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build(
                "Please click on the below url to activate your account on  : " + ACTIVATION_EMAIL_API + "/" + token,
                "signup-template");

        mailService.sendMail(
                new NotificationEmail("Please activate the account via link in email", user.getEmail(), message));
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verify_token = verificationTokenRepository.findByToken(token);

        verify_token.orElseThrow(() -> new BaseException("Token is invalid"));
        verifyUser(verify_token.get());
    }

    private void verifyUser(VerificationToken token) {
        String username = token.getUser().getUsername();
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException("No user found with this username: " + username));

        user.setEnabled(true);
        userRepository.save(user);
    }

    private boolean isUserExists(String email, String username) {
        if (userRepository.existsByEmail(email) || userRepository.existsByUsername(username)) {
            throw new BaseException("User already exists");
        }

        return false;
    }

    private String generateVerificationToken(UserModel user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);

        return token;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}