package de.octave.backend.verification.phone;

public interface SmsSender {

    void sendSms(SmsRequest request, String message);
}
