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
    @Value("${TWILIO_ACCOUNT_SID}")
    private String accountSid;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String authToken;

    @Value("${TWILIO_PHONE_NUMBER}")
    private String fromTwilioPhoneNumber;

    @Value("${MY_PHONE_NUMBER}")
    private String toPhoneNumber;
}
