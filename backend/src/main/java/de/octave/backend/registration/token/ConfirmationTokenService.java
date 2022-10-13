package de.octave.backend.registration.token;

import de.octave.backend.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;

    public void saveConfirmationToken(ConfirmationToken token){
        repository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return repository.findByToken(token);
    }

    public Optional<ConfirmationToken> getTokenByUserId(User user){
        return repository.findByUserId(user);
    }

    public void setConfirmedAt(String token) {
        repository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public void delete(ConfirmationToken token){
        repository.delete(token);
    }
}
