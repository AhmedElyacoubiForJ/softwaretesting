package edu.yacoubi.softwaretesting.payment;

import edu.yacoubi.softwaretesting.customer.Customer;
import edu.yacoubi.softwaretesting.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

// testing scenarios from bottom to top
// equivalent to the service implementation
class PaymentServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock // is a interface & we will test it in isolation later
    private CardPaymentCharger cardPaymentCharger;

    private PaymentService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new PaymentService(
                customerRepository,
                paymentRepository,
                cardPaymentCharger
        );
    }

    @Test // Testing Charge Card Happy Path
    void itShouldChargeCardSuccessfully() {
        // Given
        // we need a valid customer
        UUID customerId = UUID.randomUUID();
        // ... customer exists
        given(customerRepository.findById(customerId))
                // don't need a customer, so we mock it.
                .willReturn(Optional.of(mock(Customer.class)));
        // we know now that the first scenario
        // according to the payment service implementation will pass

        // ... payment request
        PaymentRequest request = new PaymentRequest(
                new Payment(
                        null,
                        null,
                        new BigDecimal("100.00"),
                        Currency.USD,
                        "card123xx",
                        "Donation"
                )
        );

        // ... card is charged successfully
        given(cardPaymentCharger.chargeCard(
                request.getPayment().getSource(),
                request.getPayment().getAmount(),
                request.getPayment().getCurrency(),
                request.getPayment().getDescription()
        )).willReturn(new CardPaymentCharge(true));
        // at this point every thing is fine

        // When we charge a card ...
        underTest.chargeCard(customerId, request);

        // Then ... what can happen, we set the customer to the payment
        // And we save the payment
        // So the test must capture the payment and make sure that
        // the customer set to it
        ArgumentCaptor<Payment> paymentArgumentCaptor =
                ArgumentCaptor.forClass(Payment.class);

        then(paymentRepository)
                .should()
                .save(paymentArgumentCaptor.capture());

        Payment paymentArgumentCaptorValue = paymentArgumentCaptor.getValue();

        assertThat(paymentArgumentCaptorValue)
                .isEqualToIgnoringGivenFields(
                        request.getPayment(),
                        "customerId"
                );

        assertThat(paymentArgumentCaptorValue.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    void itShouldThrowWhenCardIsNotCharged() {
        // Given
        UUID customerId = UUID.randomUUID();
        // ... customer exists
        given(customerRepository.findById(customerId))
                // don't need a customer, so we mock it.
                .willReturn(Optional.of(mock(Customer.class)));

        // ... payment request
        PaymentRequest request = new PaymentRequest(
                new Payment(
                        null,
                        null,
                        new BigDecimal("100.00"),
                        Currency.USD,
                        "card123xx",
                        "Donation"
                )
        );

        // ... card is not charged successfully
        given(cardPaymentCharger.chargeCard(
                request.getPayment().getSource(),
                request.getPayment().getAmount(),
                request.getPayment().getCurrency(),
                request.getPayment().getDescription()
        )).willReturn(new CardPaymentCharge(false));

        // When
        // Then
        assertThatThrownBy(
                () -> underTest.chargeCard(customerId, request)
        )
                .hasMessageContaining("Card not debited for customer " + customerId)
                .isInstanceOf(IllegalStateException.class);

        then(paymentRepository)
                .should(never())
                .save(any(Payment.class));
    }

    @Test
    void itShouldNotChargeCardAndThrowWhenCurrencyNoSupported() {
        // Given
        UUID customerId = UUID.randomUUID();
        // ... customer exists
        given(customerRepository.findById(customerId))
                .willReturn(Optional.of(mock(Customer.class)));

        // ... payment request
        Currency currency = Currency.EUR;
        PaymentRequest request = new PaymentRequest(
                new Payment(
                        null,
                        null,
                        new BigDecimal("100.00"),
                        currency,
                        "card123xx",
                        "Donation"
                )
        );

        // When
        assertThatThrownBy(
                () -> underTest.chargeCard(customerId, request)
        )
                .hasMessageContaining("currency " + currency + " not supported")
                .isInstanceOf(IllegalStateException.class);

        // Then
        // ... No interactions with cardPaymentCharger
        then(cardPaymentCharger).shouldHaveNoInteractions();

        // ... No interactions with paymentRepository
        then(paymentRepository).shouldHaveNoInteractions();
    }

    @Test
    void itShouldNotChargeAndThrowWhenCustomerNotFound() {
        // Given
        UUID customerId = UUID.randomUUID();
        // ... customer not found in db
        given(customerRepository.findById(customerId))
                .willReturn(Optional.empty());

        // When
        assertThatThrownBy(
                () -> underTest.chargeCard(customerId, new PaymentRequest(new Payment()))
        )
                .hasMessageContaining("customer " + customerId +" not found")
                .isInstanceOf(IllegalStateException.class);

        // Then
        // ... No interactions with cardPaymentCharger nor paymentRepository
        then(cardPaymentCharger).shouldHaveNoInteractions();
        then(paymentRepository).shouldHaveNoInteractions();
    }
}