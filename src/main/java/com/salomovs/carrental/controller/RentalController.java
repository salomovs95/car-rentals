package com.salomovs.carrental.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.carrental.model.dto.RentalPlacementDto;
import com.salomovs.carrental.model.entity.Customer;
import com.salomovs.carrental.model.entity.Rental;
import com.salomovs.carrental.model.entity.Vehicle;
import com.salomovs.carrental.model.repository.RentalRepository;
import com.salomovs.carrental.service.*;

import lombok.RequiredArgsConstructor;

@RestController @RequestMapping("/rentals") @RequiredArgsConstructor
public class RentalController {
  private final RentalService rentalService;
  private final CustomerService customerService;
  private final VehicleService vehicleService;

  @PostMapping("/place/{customerId}/{vehicleId}")
  public ResponseEntity<Void> placeARent(Integer customerId, Integer vehicleId) {
    Customer c = customerService.findCustomer(customerId);
    Vehicle v = vehicleService.retrieveVehicleInfo(vehicleId);

    rentalService.placeRental(new RentalPlacementDto(c, v));
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<Page<Rental>> getRentalList(@RequestParam(name="page") Optional<Integer> pageNumber,
                                                    @RequestParam(name="limit") Optional<Integer> pageSize) {
    Pageable page = PageRequest.of(pageNumber.orElse(0), pageSize.orElse(20));
    Page<Rental> res = rentalService.listRentals(page);
    return ResponseEntity.status(HttpStatus.OK).body(res);
  }

  @GetMapping("/{rentalId}")
  public ResponseEntity<Rental> getRentalInfo(@PathVariable Integer rentalId) {
    Rental rental = rentalService.findRental(rentalId);
    return ResponseEntity.status(HttpStatus.OK).body(rental);
  }
}
