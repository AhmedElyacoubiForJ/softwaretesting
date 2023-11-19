package edu.yacoubi.softwaretesting.customer;

import edu.yacoubi.softwaretesting.utils.PhoneNumberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerRegistrationServiceTest {
    // test the class in isolation using a Mock for dependencies
    // and classes that already tested
    @Mock
    private CustomerRepository customerRepository;//  = mock(CustomerRepository.class);

    @Mock
    private PhoneNumberValidator phoneNumberValidator;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    private CustomerRegistrationService underTest;

    @BeforeEach
    void setUp() {
        underTest =
                new CustomerRegistrationService(
                        customerRepository,
                        phoneNumberValidator
                );
    }

    @Test
    void canGetAllCustomers() {
        // to test whether repo. findAll was called
        // Given
        // When
        underTest.getAllCustomers();
        // Then
        verify(customerRepository).findAll();
    }

    @Test
    void itShouldSaveNewCustomer() { // testing the happy path
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer =
                new Customer(UUID.randomUUID(), "joe", phoneNumber);
        // ... request
        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(customer);

        // When a service call a mock repository it will return
        // an empty optional
        // ... no customer with phone number passed
        // we mocked the return
        given(customerRepository
                .selectCustomerByPhoneNumber(phoneNumber)
        ).willReturn(Optional.empty());

        // ...valid phone number
        given(phoneNumberValidator.test(any())).willReturn(true);

        // when
        underTest.registerNewCustomer(request);

        // Then (when empty should save)
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).isEqualTo(customer);
    }

    @Test
    void itShouldNotSaveNewCustomerWhenPhoneNumberIsInValid() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer =
                new Customer(UUID.randomUUID(), "joe", phoneNumber);
        // ... request
        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(customer);

        // ...valid phone number
        given(phoneNumberValidator.test(any())).willReturn(false);

        // When
        // Then
        assertThatThrownBy(
                () -> underTest.registerNewCustomer(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(
                        String.format("Phone Number [%s] is not valid", phoneNumber)
                );

        // Finally
        then(customerRepository).shouldHaveNoInteractions();
        //then(customerRepository).should(never()).save(any(Customer.class));
    }

    @Test
    void itShouldSaveNewCustomerWhenIdIsNull() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(
                null,
                "joe",
                phoneNumber
        );

        // ... a request
        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(customer);

        // ... No customer with phone number passed
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.empty());

        // ...valid phone number
        given(phoneNumberValidator.test(any())).willReturn(true);

        // When
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue)
                .isEqualToIgnoringGivenFields(customer, "id");
        assertThat(customerArgumentCaptorValue.getId()).isNotNull();
    }

    @Test
    void itShouldNotSaveCustomerWhenCustomerExists() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(
                UUID.randomUUID(),
                "Joe",
                phoneNumber
        );

        // ... a request
        CustomerRegistrationRequest request =
                new CustomerRegistrationRequest(customer);

        // ... an existing customer is returned
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customer));

        // ...valid phone number
        given(phoneNumberValidator.test(any())).willReturn(true);

        // When
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should(never()).save(any());
        // or
        // mock has one interaction with selectCustomerByPhoneNumber
        // and no more one
        // then(customerRepository).should().selectCustomerByPhoneNumber(phoneNumber);
        // then(customerRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void itShouldThrowWhenPhoneNumberIsTaken() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(
                UUID.randomUUID(),
                "Maryam",
                phoneNumber
        );
        Customer customerTwo = new Customer(
                UUID.randomUUID(),
                "John",
                phoneNumber
        );

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... There is a customer with phone number passed
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customerTwo));

        // ...valid phone number
        given(phoneNumberValidator.test(any())).willReturn(true);

        // When
        // Then
        assertThatThrownBy(
                () -> underTest.registerNewCustomer(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(
                        String.format("phone number [%s] is taken", phoneNumber)
                );

        // Finally
        then(customerRepository).should(never()).save(any(Customer.class));
    }
}