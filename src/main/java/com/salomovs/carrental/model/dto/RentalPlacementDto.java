package com.salomovs.carrental.model.dto;

import com.salomovs.carrental.model.entity.Customer;
import com.salomovs.carrental.model.entity.Vehicle;

public record RentalPlacementDto(
  Customer customer,
  Vehicle vehicle
) {}
