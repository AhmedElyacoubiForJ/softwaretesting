package edu.yacoubi.softwaretesting.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CustomerRegistrationServiceTest {
    // test the class in isolation using a Mock for dependencies
    // and classes that already tested
    @Mock
    private CustomerRepository customerRepository;//  = mock(CustomerRepository.class);

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
}