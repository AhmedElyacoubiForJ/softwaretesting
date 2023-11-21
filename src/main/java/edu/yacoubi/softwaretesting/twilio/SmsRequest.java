package edu.yacoubi.softwaretesting.twilio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
@ToString
public class SmsRequest {

    @JsonProperty("phoneNumber")
    @NotBlank
    private final String phoneNumber; // destination

    @JsonProperty("message")
    @NotBlank
    private final String message;
}
