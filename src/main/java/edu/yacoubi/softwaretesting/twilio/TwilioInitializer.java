package edu.yacoubi.softwaretesting.twilio;

import com.twilio.Twilio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class TwilioInitializer {
    @Autowired
    private final TwilioConfiguration twilioConf;

    public TwilioInitializer(TwilioConfiguration twilioConf) {
        this.twilioConf = twilioConf;
//        Twilio.init(
//                twilioConf.getAccountSid(),
//                twilioConf.getAuthToken()
//        );
        log.info(
                "Environment variables...  {}",
                twilioConf
        );
        log.info(
                "twilio initialized... with account sid {}",
                twilioConf.getAccountSid()
        );
    }
}
