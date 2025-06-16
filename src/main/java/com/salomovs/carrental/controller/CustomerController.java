package com.salomovs.carrental.controller;

import jakarta.validation.Valid;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.carrental.annotation.ApiGetOperation;
import com.salomovs.carrental.annotation.ApiPostOperation;
import com.salomovs.carrental.model.dto.NewCustomerDto;
import com.salomovs.carrental.model.dto.PageableResponse;
import com.salomovs.carrental.model.entity.Customer;
import com.salomovs.carrental.service.CustomerService;

@RequiredArgsConstructor @RestController @RequestMapping("/customers")
public class CustomerController {
  private final CustomerService customerService;

  @PostMapping @ApiPostOperation(summary="New customer registration")
  public ResponseEntity<Void> registerCustomer(@RequestBody @Valid NewCustomerDto body) {
    customerService.registerCustomer(body);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping @ApiGetOperation(summary="Retrieves a paginated list of Customers")
  public ResponseEntity<PageableResponse<Customer>> listCustomers(@RequestParam Optional<Integer> page) {
    int pageNumber = page.orElse(0);
    Pageable pageRequest = PageRequest.of(pageNumber, 20);
    PageableResponse<Customer> response = customerService.retrieveCustomersList(pageRequest);
    
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
