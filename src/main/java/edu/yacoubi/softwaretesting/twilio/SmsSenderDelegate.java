package edu.yacoubi.softwaretesting.twilio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("smsDelegate")
@Slf4j
public class SmsSenderDelegate {

    private final SmsSender smsSender;

    public SmsSenderDelegate(@Qualifier("mockSmsSender") SmsSender smsSender) {

        this.smsSender = smsSender;
    }

    public void sendSms(SmsRequest request) {
        smsSender.sendSms(request);
    }
}
