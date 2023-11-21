package edu.yacoubi.softwaretesting.twilio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("mockSmsSender")
@Slf4j
public class MockSmsSender implements SmsSender {
    @Override
    public void sendSms(SmsRequest smsRequest) {
        log.info("MockSmsSender: sendSms ({})", smsRequest);
    }
}
