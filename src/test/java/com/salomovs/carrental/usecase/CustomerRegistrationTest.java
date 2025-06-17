package com.salomovs.carrental.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.salomovs.carrental.model.dto.NewCustomerDto;
import com.salomovs.carrental.model.entity.Customer;
import com.salomovs.carrental.model.repository.CustomerRepository;
import com.salomovs.carrental.service.CustomerService;

@SpringBootTest @Tag("UNIT_TESTS") @TestInstance(Lifecycle.PER_CLASS)
public class CustomerRegistrationTest {
  @MockitoBean
  private CustomerRepository crMock;
  private CustomerService customerService;

  @BeforeAll
  void setupOnce() {
    customerService = new CustomerService(crMock);
  }

  @Test
  void RegisterCustomerSuccessfull() {
    when(crMock.save(any(Customer.class)))
      .thenReturn(new Customer(998, "tax_id_001", "customer_name_001", "customer_001@email.com", "customer_phone_001"));
    
    var dto = new NewCustomerDto("tax_id_001", "customer_name_001", "customer_phone_001", "customer_001@email.com");
    assertDoesNotThrow(()->customerService.registerCustomer(dto));
  }
}
