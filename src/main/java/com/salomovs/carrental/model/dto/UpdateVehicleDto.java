package com.salomovs.carrental.model.dto;

import java.util.Optional;

import jakarta.validation.constraints.Min;

public record UpdateVehicleDto(
  @Min(100) Optional<Integer> hourlyPrice,
  @Min(100) Optional<Integer> dailyPrice
) {}
