package edu.yacoubi.softwaretesting.twilio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsSenderDelegate smsSenderDelegate;

    @PostMapping
    public void sendSms(@RequestBody SmsRequest smsRequest) {
        smsSenderDelegate.sendSms(smsRequest);
    }
}
