package com.salomovs.carrental.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salomovs.carrental.exception.InvoiceProcessingException;
import com.salomovs.carrental.exception.RentalNotFoundException;
import com.salomovs.carrental.model.dto.InvoiceResponseDto;
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
    Rental rental = rentalRepository.findById(rentalId)
                                    .orElseThrow(RentalNotFoundException::new);

    LocalDateTime returnDate = LocalDateTime.now();

    if (returnDate.isBefore(rental.getRentAt())) {
      throw new InvoiceProcessingException("Invalid return date");
    }

    if (returnDate.getDayOfMonth()==rental.getRentAt().getDayOfMonth() &&
        returnDate.getMonthValue()==rental.getRentAt().getMonthValue() &&
        returnDate.getYear()==rental.getRentAt().getYear()) {
      throw new InvoiceProcessingException("Can't return vehicle same day of it's rental");
    }

    rental.setReturnAt(LocalDateTime.now());
    rentalRepository.save(rental);

    log.debug(String.format("Rental %d successfully ended", rental.getId()));
  }

  public PageableResponse<InvoiceResponseDto> listRentals(Pageable page) {
    Page<Rental> data = rentalRepository.findAll(page);
    List<InvoiceResponseDto> content = data.getContent()
                                           .stream()
                                           .map(InvoiceResponseDto::parse)
                                           .collect(Collectors.toList());
    return new PageableResponse<>(data.hasNext(), data.hasPrevious(), content);
  }

  public InvoiceResponseDto findRental(Integer rentalId) {
    Rental rental = rentalRepository.findById(rentalId)
                                    .orElseThrow(RentalNotFoundException::new);

    return InvoiceResponseDto.parse(rental);
  }
}
