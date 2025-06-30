package com.salomovs.carrental.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.salomovs.carrental.model.dto.NewCustomerDto;
import com.salomovs.carrental.model.repository.CustomerRepository;
import com.salomovs.carrental.service.CustomerService;

@SpringBootTest @Tag("UNIT_TESTS") @TestInstance(Lifecycle.PER_CLASS)
public class CustomerRegistrationTest {
  @Autowired
  private CustomerRepository crMock;
  private CustomerService customerService;

  @BeforeAll
  void setupOnce() {
    customerService = new CustomerService(crMock);
  }

  @Test
  void RegisterCustomerSuccessfull() {
    var dto = new NewCustomerDto("tax_id_001", "customer_name_001", "customer_phone_001", "customer_001@email.com");
    assertDoesNotThrow(()->customerService.registerCustomer(dto));
  }
}
