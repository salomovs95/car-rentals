package com.salomovs.carrental.controller;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.carrental.annotation.ApiGetOperation;
import com.salomovs.carrental.annotation.ApiPatchOperation;
import com.salomovs.carrental.annotation.ApiPostOperation;
import com.salomovs.carrental.model.dto.InvoiceResponseDto;
import com.salomovs.carrental.model.dto.PageableResponse;
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

  @PostMapping("/place/{customerId}/{vehicleId}") @ApiPostOperation(summary="Place a new rental")
  public ResponseEntity<Void> placeARent(Integer customerId, Integer vehicleId) {
    Customer c = customerService.findCustomer(customerId);
    Vehicle v = vehicleService.retrieveVehicleInfo(vehicleId);

    rentalService.placeRental(new RentalPlacementDto(c, v));
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping("/{rentalId}") @ApiPatchOperation(summary="Return vehicle")
  public ResponseEntity<Void> returnVehicle(Integer rentalId) {
    rentalService.returnVehicle(rentalId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping @ApiGetOperation(summary="Retrieve a rental's paginated list")
  public ResponseEntity<PageableResponse<InvoiceResponseDto>> getRentalList(@RequestParam(name="page") Optional<Integer> pageNumber, @RequestParam(name="limit") Optional<Integer> pageSize) {
    Pageable page = PageRequest.of(pageNumber.orElse(0), pageSize.orElse(20));
    PageableResponse<InvoiceResponseDto> res = rentalService.listRentals(page);
    return ResponseEntity.status(HttpStatus.OK).body(res);
  }

  @GetMapping("/{rentalId}") @ApiGetOperation(summary="Retrieve info 'bout a given rental")
  public ResponseEntity<InvoiceResponseDto> getRentalInfo(@PathVariable Integer rentalId) {
    InvoiceResponseDto invoice = rentalService.findRental(rentalId);
    return ResponseEntity.status(HttpStatus.OK).body(invoice);
  }
}
