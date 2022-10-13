package de.octave.backend.registration;

import lombok.Getter;

@Getter
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String email;
    private String token = "";

    public RegistrationRequest(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String password() {
        return password;
    }

    public String email() {
        return email;
    }

    public String token() {
        return token;
    }
}
