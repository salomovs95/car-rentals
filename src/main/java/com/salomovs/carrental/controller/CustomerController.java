package com.salomovs.carrental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.carrental.annotation.ApiPostOperation;
import com.salomovs.carrental.model.dto.NewCustomerDto;
import com.salomovs.carrental.service.CustomerService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor @RestController("/customers")
public class CustomerController {
  private final CustomerService customerService;

  @PostMapping @ApiPostOperation(summary="New customer registration")
  public ResponseEntity<Void> registerCustomer(@RequestBody @Valid NewCustomerDto body) {
    customerService.registerCustomer(body);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
