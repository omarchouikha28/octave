package de.octave.backend.verification.email;

public interface EmailSender {
    void send(String to, String email);
}
