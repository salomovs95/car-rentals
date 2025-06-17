package com.salomovs.carrental.usecase;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.salomovs.carrental.exception.CustomerNotFoundException;
import com.salomovs.carrental.model.entity.Customer;
import com.salomovs.carrental.model.repository.CustomerRepository;
import com.salomovs.carrental.service.CustomerService;

@SpringBootTest @Tag("UNIT_TESTS") @TestInstance(Lifecycle.PER_CLASS)
public class CustomerInfoRetrievalTest {
  @MockitoBean
  private CustomerRepository crMock;
  private CustomerService customerService;

  @BeforeAll
  void setupOnce() {
    customerService = new CustomerService(crMock);
  }

  @Test
  void FindACustomerSuccessfull() {
    int customerId = 1864;
    when(crMock.findById(anyInt()))
      .thenReturn(Optional.of(new Customer(customerId, "customer_999_tax_id", "Customer 999", "customer_999@email.com", "customer_999_phone")));

    assertDoesNotThrow(()->customerService.findCustomer(customerId));
  }

  @Test
  void FindACustomerFails() {
    int customerId = 1995;
    when(crMock.findById(anyInt()))
      .thenReturn(Optional.empty());

    assertThrows(CustomerNotFoundException.class, ()->customerService.findCustomer(customerId));
  }
}
