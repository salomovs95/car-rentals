package com.salomovs.carrental.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salomovs.carrental.exception.CustomerNotFoundException;
import com.salomovs.carrental.model.dto.NewCustomerDto;
import com.salomovs.carrental.model.dto.PageableResponse;
import com.salomovs.carrental.model.dto.UpdateCustomerDto;
import com.salomovs.carrental.model.entity.Customer;
import com.salomovs.carrental.model.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class CustomerService {
  private final CustomerRepository customerRepository;

  public void registerCustomer(NewCustomerDto dto) {
    Customer customer = new Customer(
      null,
      dto.taxId(),
      dto.fullName(),
      dto.email(),
      dto.phone()
    );

    Integer newCustomerId = customerRepository.save(customer).getId();
    log.debug(String.format("New customer registered successfully under ID: %d", newCustomerId));
  }

  public PageableResponse<Customer> retrieveCustomersList(Pageable page) {
    Page<Customer> res = customerRepository.findAll(page);
    return new PageableResponse<Customer>(res.hasNext(), res.hasPrevious(), res.getContent());
  }

  public void updateCustomer(Integer customerId, UpdateCustomerDto dto) {
    Customer customer = customerRepository.findById(customerId)
                                          .orElseThrow(CustomerNotFoundException::new);
    
    if (dto.email().isPresent()) customer.setEmail(dto.email().get());
    if (dto.phone().isPresent()) customer.setPhone(dto.phone().get());

    customerRepository.save(customer);
  }
}
