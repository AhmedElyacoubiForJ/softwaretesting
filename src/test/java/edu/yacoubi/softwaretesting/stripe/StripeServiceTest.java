package edu.yacoubi.softwaretesting.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import edu.yacoubi.softwaretesting.payment.CardPaymentCharge;
import edu.yacoubi.softwaretesting.payment.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class StripeServiceTest {

    private StripeService underTest;

    @Mock
    private StripeApi stripeApi;

    @BeforeEach
    void setUp() {
        underTest = new StripeService(stripeApi);
    }

    @Test
    void itShouldChargeCard() throws StripeException {
        // Given
        String cardSource = "0x0x0x";
        BigDecimal amount = new BigDecimal("10.00");
        Currency currency = Currency.USD;
        String description = "Zakat";

        // Successful charge
        Charge charge = new Charge();
        charge.setPaid(true);
        given(stripeApi.create(anyMap(), any())).willReturn(charge);

        // When
        CardPaymentCharge cardPaymentCharge = underTest
                .chargeCard(
                        cardSource,
                        amount,
                        currency,
                        description
                );

        // Then
        ArgumentCaptor<Map<String, Object>> mapArgumentCaptor = ArgumentCaptor
                .forClass(Map.class);
        ArgumentCaptor<RequestOptions> optionsArgumentCaptor = ArgumentCaptor
                .forClass(RequestOptions.class);

        // Captor requestMap and options
        then(stripeApi)
                .should()
                .create(
                        mapArgumentCaptor.capture(),
                        optionsArgumentCaptor.capture()
                );

        // Assert on requestMap
        Map<String, Object> requestMap = mapArgumentCaptor.getValue();

        assertThat(requestMap.keySet()).hasSize(4);

        assertThat(requestMap.get("amount")).isEqualTo(amount);
        assertThat(requestMap.get("currency")).isEqualTo(currency);
        assertThat(requestMap.get("source")).isEqualTo(cardSource);
        assertThat(requestMap.get("description")).isEqualTo(description);

        // Assert on options
        RequestOptions options = optionsArgumentCaptor.getValue();

        assertThat(options).isNotNull();

        // card is debited successfully
        assertThat(cardPaymentCharge).isNotNull();
        assertThat(cardPaymentCharge.isCardDebited()).isTrue();
    }
}