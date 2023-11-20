package edu.yacoubi.softwaretesting.twilio;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//@NoArgsConstructor
@Getter
@Setter
@ToString
public class TwilioConfiguration {
    @Value("${accountSid}")
    private String accountSid;

    @Value("${authToken}")
    private String authToken;

    @Value("${trialPhoneNumber}")
    private String trialPhoneNumber;

    public TwilioConfiguration() {
//        accountSid = System.getenv("accountSid");
//        authToken = System.getenv("authToken");
//        trialPhoneNumber = System.getenv("trialPhoneNumber");
    }
}
