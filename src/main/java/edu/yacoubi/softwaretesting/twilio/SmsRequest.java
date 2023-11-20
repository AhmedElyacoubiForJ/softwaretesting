package edu.yacoubi.softwaretesting.twilio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class SmsRequest {
    @JsonProperty("phoneNumber")
    private final String phoneNumber; // destination
    @JsonProperty("message")
    private final String message;
}
