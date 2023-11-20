package edu.yacoubi.softwaretesting.twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

// A good way to mock out any interaction with Twilio
// would be to create your own class (MockClient) that extends HttpClient.
// Then you would create a new TwilioRestClient that uses your MockClient.
// Now if you use the new TwilioRestClient you can have the MockClient
// return whatever response that you would like!

@Service("twilio")
@RequiredArgsConstructor
@Slf4j
public class TwilioSmsSender implements SmsSender {
    private final TwilioConfiguration twilioConf;

    @Override
    public void sendSms(SmsRequest smsRequest) {

        String phoneNumber = smsRequest.getPhoneNumber(); // destination

        if(!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException(
                    String.format("Phone number [%s] is not valid", phoneNumber)
            );
        }

        PhoneNumber to = new PhoneNumber(phoneNumber);
        PhoneNumber from = new PhoneNumber(twilioConf.getTrialPhoneNumber());
        String message = smsRequest.getMessage();

        MessageCreator creator = Message.creator(to, from, message);
        //creator.create();
        log.info("TwilioSmsSender: Send sms {}", smsRequest);
    }

    // TODO: implement phone number validator
    private boolean isValidPhoneNumber(String phoneNumber) {
        return true;
    }
}
