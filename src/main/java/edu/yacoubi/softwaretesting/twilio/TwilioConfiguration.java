package edu.yacoubi.softwaretesting.twilio;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TwilioConfiguration {
    @Value("${account.sid}")
    private String accountSid;

    @Value("${auth.token}")
    private String authToken;

    @Value("${trial.phone-number}")
    private String trialPhoneNumber;
}
