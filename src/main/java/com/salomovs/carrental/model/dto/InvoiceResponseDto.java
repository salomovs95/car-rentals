package com.salomovs.carrental.model.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.salomovs.carrental.model.entity.Customer;
import com.salomovs.carrental.model.entity.Rental;
import com.salomovs.carrental.model.entity.Vehicle;

public record InvoiceResponseDto(
  Integer rent_id,
  Integer subTotal,
  Integer taxes,
  Integer totals,
  LocalDateTime rentAt,
  LocalDateTime returnAt,
  Customer customer,
  Vehicle vehicle
) {
  private Integer period() {
    if (returnAt() == null) return 0;
    return Integer.valueOf(""+rentAt().until(returnAt(), ChronoUnit.HOURS));
  }

  public Integer taxes() {
    if (returnAt() == null) return 0;
    double taxRate = period() > 12 ? 0.25 : 0.2;
    return (int) ((subTotal()/100) * taxRate * 100);
  }

  public Integer subTotal() {
    if (returnAt() == null) return 0;

    Vehicle v = vehicle();

    int period = period();
    int basePrice = period > 12 ? v.getDailyPrice() : v.getHourPrice();

    if (period > 12) return Math.ceilDiv(period, 24) * (basePrice/100) * 100;
    return (period) * (basePrice/100) * 100;
  }

  public Integer totals() {
    if (returnAt() == null) return 0;
    return subTotal() + taxes();
  }

  public static InvoiceResponseDto parse(Rental rental) {
    return new InvoiceResponseDto(
      rental.getId(),
      0,
      0,
      0,
      rental.getRentAt(),
      rental.getReturnAt(),
      rental.getCustomer(),
      rental.getVehicle());
  }
}
