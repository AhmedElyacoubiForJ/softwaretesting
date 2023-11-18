package edu.yacoubi.softwaretesting.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PutMapping
    public void makePayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.chargeCard(paymentRequest.getPayment().getCustomerId(), paymentRequest);
    }
}
