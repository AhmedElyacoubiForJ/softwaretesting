package edu.yacoubi.softwaretesting.twilio;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/sms")
@RequiredArgsConstructor
@Slf4j
public class SmsController {

    private final SmsSenderDelegate smsSenderDelegate;

    @PostMapping
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {

        smsSenderDelegate.sendSms(smsRequest);
        log.info("SmsController: sms {} send", smsRequest);
    }
}
