package com.salomovs.carrental.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.salomovs.carrental.exception.CustomerNotFoundException;
import com.salomovs.carrental.model.repository.CustomerRepository;
import com.salomovs.carrental.service.CustomerService;

@Sql(scripts="/seed.sql", executionPhase=ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest @Tag("UNIT_TESTS") @TestInstance(Lifecycle.PER_CLASS)
public class CustomerInfoRetrievalTest {
  @Autowired
  private CustomerRepository crMock;
  private CustomerService customerService;

  @BeforeAll
  void setupOnce() {
    customerService = new CustomerService(crMock);
  }

  @Test
  void FindACustomerSuccessfull() {
    int customerId = 996;
    assertDoesNotThrow(()->customerService.findCustomer(customerId));
  }

  @Test
  void FindACustomerFails() {
    int customerId = 1995;
    assertThrows(CustomerNotFoundException.class, ()->customerService.findCustomer(customerId));
  }
}
