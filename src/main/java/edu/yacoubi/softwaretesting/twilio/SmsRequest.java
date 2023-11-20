package edu.yacoubi.softwaretesting.twilio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class SmsRequest {
    private final String phoneNumber; // destination
    private final String message;
}
