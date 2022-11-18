package br.com.mardoqueu.redditclone.service;

import br.com.mardoqueu.redditclone.dto.RegisterRequest;
import br.com.mardoqueu.redditclone.exception.SpringRedditException;
import br.com.mardoqueu.redditclone.model.NotificationEmail;
import br.com.mardoqueu.redditclone.model.User;
import br.com.mardoqueu.redditclone.model.VerificationToken;
import br.com.mardoqueu.redditclone.repository.UserRepository;
import br.com.mardoqueu.redditclone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

import static java.time.Instant.now;
@Service
@AllArgsConstructor
@Slf4j

/*Inside the AuthService class, we are mapping the RegisterRequest object to the User object and when setting the password,
  we are calling the encodePassword() method.*/
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private  final MailService mailService;
    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreated(now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Active your Account", user.getEmail(), "Thank you for signing up to Spring Reddit, please click on the below url to activate your account: " +  "http://localhost:8080/api/auth/accountVerification" + "/" + token));

    }

/*  We added the generateVerificationToken() method and calling that method right after we saved the user into
    UserRepository. Note that, we are creating a random UUID as our token, creating an object for VerificationToken, fill in the
    data for that object and save it into the VerificationTokenRepository. As we have the token, now its time to send an email
    that contains this verification token.*/
    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

/*
    This method is using the BCryptPasswordEncoder to encode our password.
*/
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User Not Found with id - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }
}