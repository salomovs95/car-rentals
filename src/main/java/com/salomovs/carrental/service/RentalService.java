package com.salomovs.carrental.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salomovs.carrental.exception.RentalNotFoundException;
import com.salomovs.carrental.model.dto.PageableResponse;
import com.salomovs.carrental.model.dto.RentalPlacementDto;
import com.salomovs.carrental.model.entity.Rental;
import com.salomovs.carrental.model.repository.RentalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class RentalService {
  private final RentalRepository rentalRepository;

  public void placeRental(RentalPlacementDto dto) {
    Rental newRental = new Rental(LocalDateTime.now(), dto.customer(), dto.vehicle());
    int rentalId = rentalRepository.save(newRental).getId();

    log.debug("Vehicle rental successfully placed. Operation ID: " + rentalId);
  }

  public void returnVehicle(Integer rentalId) {
    Rental rental = findRental(rentalId);

    rental.setReturnAt(LocalDateTime.now());
    int amountToPay = calculateAmountToPay(rental);
    rental.setAmountToPay(amountToPay);
    rentalRepository.save(rental);

    log.debug(String.format("Rental %d successfully ended", rental.getId()));
  }

  public PageableResponse<Rental> listRentals(Pageable page) {
    Page<Rental> data = rentalRepository.findAll(page);
    return new PageableResponse<>(data.hasNext(), data.hasPrevious(), data.getContent());
  }

  public Rental findRental(Integer rentalId) {
    Rental rental = rentalRepository.findById(rentalId)
                                    .orElseThrow(RentalNotFoundException::new);

    return rental;
  }

  private int calculateAmountToPay(Rental rental) {
    long period = rental.getRentAt().until(rental.getReturnAt(), ChronoUnit.HOURS);
    if (period > 12) {
      return Math.ceilDiv((int) period, 24) * (rental.getVehicle().getDailyPrice()/100) * 100;
    }
    return ((int) period) * (rental.getVehicle().getHourPrice()/100) * 100;
  }
}
