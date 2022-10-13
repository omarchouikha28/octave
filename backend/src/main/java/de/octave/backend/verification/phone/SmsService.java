package de.octave.backend.verification.phone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final SmsSender smsSender;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSms(SmsRequest request, String message){
        smsSender.sendSms(request, message);
    }

}
