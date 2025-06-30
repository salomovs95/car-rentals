package com.salomovs.carrental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.carrental.model.dto.RentalPlacementDto;
import com.salomovs.carrental.model.entity.Customer;
import com.salomovs.carrental.model.entity.Vehicle;
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
}
