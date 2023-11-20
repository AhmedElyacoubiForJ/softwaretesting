package edu.yacoubi.softwaretesting.twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwilioSmsSender implements SmsSender {
    private final TwilioConfiguration twilioConfiguration;

    @Override
    public void sendSms(SmsRequest request) {
        PhoneNumber to = new PhoneNumber(request.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfiguration.getFromTwilioPhoneNumber());
        String message = request.getMessage();

        MessageCreator creator = Message.creator(to, from, message);
        //creator.create();
    }
}
