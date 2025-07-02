package com.salomovs.carrental.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salomovs.carrental.exception.RentalNotFoundException;
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

  public Page<Rental> listRentals(Pageable page) {
    return rentalRepository.findAll(page);
  }

  public Rental findRental(Integer rentalId) {
    Rental rental = rentalRepository.findById(rentalId)
                                    .orElseThrow(RentalNotFoundException::new);

    return rental;
  }
}
