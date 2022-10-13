package de.octave.backend.user;

import de.octave.backend.registration.token.ConfirmationToken;
import de.octave.backend.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.lang.String.*;
import static java.time.LocalDateTime.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    private final static String USER_NOT_FOUND = "User with E-mail %s not found";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(format(USER_NOT_FOUND, email))
        );
    }

    public String singUp(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            User savedUser = userRepository.findByEmail(user.getEmail()).get();
            ConfirmationToken existingToken = confirmationTokenService.getTokenByUserId(savedUser).get();
            if (!savedUser.getEnabled()
                    && existingToken.getExpiresAt().isBefore(now())) {
                confirmationTokenService.delete(existingToken);
                userRepository.delete(savedUser);
            } else
                throw new IllegalStateException("An account with this email address already exists");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                now(),
                now().plusMinutes(15),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

}
