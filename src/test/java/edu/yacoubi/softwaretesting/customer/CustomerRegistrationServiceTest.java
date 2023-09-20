package edu.yacoubi.softwaretesting.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class CustomerRegistrationServiceTest {
    // test the class in isolation using a Mock for dependencies
    // and classes that already tested
    @Mock
    private CustomerRepository customerRepository;//  = mock(CustomerRepository.class);

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    private CustomerRegistrationService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new CustomerRegistrationService(customerRepository);
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
    void itShouldSaveNewCustomer() {
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
        given(customerRepository
                .selectCustomerByPhoneNumber(phoneNumber)
        ).willReturn(Optional.empty());

        // when
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue).isEqualTo(customer);
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
}