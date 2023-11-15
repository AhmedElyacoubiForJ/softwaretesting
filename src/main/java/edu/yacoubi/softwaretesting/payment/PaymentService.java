package edu.yacoubi.softwaretesting.payment;

import edu.yacoubi.softwaretesting.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;
    private final CardPaymentCharger cardPaymentCharger;

    private final List<Currency> ACCEPTED_CURRENCIES =
            List.of(
                    Currency.USD,
                    Currency.GBP
            );

    void chargeCard(UUID customerId, PaymentRequest request) {
        Payment payment = request.getPayment();

        // 1. Does customer exist
        boolean isCustomerFound = customerRepository
                .findById(customerId)
                .isPresent();

        // if not throw
        if (!isCustomerFound) {
            String message = String.format(
                    "customer with id [%s] not found",
                    customerId
            );
            throw new IllegalStateException(message);
        }

        // 2. Do we support currency
        // ACCEPTED_CURRENCIES.contains(payment.getCurrency());
        boolean isCurrencySupported = ACCEPTED_CURRENCIES
                .stream()
                .anyMatch(c -> c.equals(payment.getCurrency()));

        // if not throw
        if (!isCurrencySupported) {
            String message = String.format(
                    "currency [%s] not supported",
                    payment.getCurrency()
            );
            throw new IllegalStateException(message);
        }

        // 3. Charge card
        CardPaymentCharge cardPaymentCharge = cardPaymentCharger
                .chargeCard(
                        payment.getSource(),
                        payment.getAmount(),
                        payment.getCurrency(),
                        payment.getDescription()
                );

        // 4. if not debited throw
        if(!cardPaymentCharge.isCardDebited()) {
            String message = String.format(
                    "Card not debited for customer with id [%s]",
                    customerId
            );
            throw new IllegalStateException(message);
        }

        // 5. insert payment
        payment.setCustomerId(customerId);

        paymentRepository.save(payment);

        // 6. TODO send sms
    }
}
