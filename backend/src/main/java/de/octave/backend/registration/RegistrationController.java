package de.octave.backend.registration;

import de.octave.backend.verification.phone.SmsRequest;
import de.octave.backend.verification.phone.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private static final String VERIFICATION_URL = "http://localhost:8080/api/v1/registration/confirm?token=";

    private final RegistrationService registrationService;
    private final SmsService service;

    @PostMapping
    public ResponseEntity<RegistrationRequest> register(@RequestBody RegistrationRequest request) {
        request.setToken(registrationService.register(request));
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @GetMapping("confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token){
        String confirmed = registrationService.confirmToken(token);
        return new ResponseEntity<>(confirmed, HttpStatus.OK);
    }

    @PostMapping("sms")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest request, @RequestParam("token") String token){
        service.sendSms(request, VERIFICATION_URL + token);
        return new ResponseEntity<>("SMS has been sent, please verify your account", HttpStatus.OK);
    }

}
