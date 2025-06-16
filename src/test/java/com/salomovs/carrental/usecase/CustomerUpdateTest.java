package com.salomovs.carrental.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.salomovs.carrental.exception.CustomerNotFoundException;
import com.salomovs.carrental.model.dto.UpdateCustomerDto;
import com.salomovs.carrental.model.entity.Customer;
import com.salomovs.carrental.model.repository.CustomerRepository;
import com.salomovs.carrental.service.CustomerService;

@SpringBootTest @Tag("CUSTOMER") @TestInstance(Lifecycle.PER_CLASS)
public class CustomerUpdateTest {
  @MockitoBean
  private CustomerRepository crMock;
  private CustomerService customerService;

  @BeforeAll
  void setupOnce() {
    customerService = new CustomerService(crMock);
  }

  @Test
  void UpdateCustomerSuccessfull() {
    when(crMock.findById(anyInt()))
      .thenReturn(Optional.of(new Customer(998, "tax_id_001", "customer_name_001", "customer_001@email.com", "customer_phone_001")));

    UpdateCustomerDto mockDto = new UpdateCustomerDto(Optional.empty(), Optional.of("+00 555 777 0167"));
   
    assertDoesNotThrow(()->customerService.updateCustomer(998, mockDto));
  }

  @Test
  void UpdateCustomerFailUserNotFound() {
    when(crMock.findById(anyInt()))
      .thenReturn(Optional.empty());

    UpdateCustomerDto mockDto = new UpdateCustomerDto(Optional.empty(), Optional.of("+00 555 777 0167"));

    assertThrows(CustomerNotFoundException.class, ()->customerService.updateCustomer(998, mockDto));

  }
}
