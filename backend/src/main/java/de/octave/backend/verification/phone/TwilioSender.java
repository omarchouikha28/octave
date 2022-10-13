package de.octave.backend.verification.phone;

import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import static com.twilio.rest.api.v2010.account.Message.creator;
import static de.octave.backend.verification.phone.SmsValidator.isPhoneNumberValid;
import static java.lang.String.format;

@Service("twilio")
@AllArgsConstructor
public class TwilioSender implements SmsSender {

    private final TwilioConfiguration twilioConfiguration;

    @Override
    public void sendSms(SmsRequest request, String message) {
        if(isPhoneNumberValid(request.getPhoneNumber())){
            PhoneNumber to = new PhoneNumber(request.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            MessageCreator creator = creator(to,
                    from,
                    format("Click on this link: %s to activate your Octave account", message));
            creator.create();
        } else
            throw new IllegalStateException("Phone number is invalid");

    }

}
