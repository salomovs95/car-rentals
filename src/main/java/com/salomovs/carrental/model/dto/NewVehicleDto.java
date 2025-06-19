package com.salomovs.carrental.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewVehicleDto(
  @NotBlank String model,
  @NotBlank String brand,
  @NotBlank String plateCode,
  @NotBlank String country,
  @NotNull @Min(100) Integer hourlyPrice,
  @NotNull @Min(100) Integer dailyPrice
) {}
