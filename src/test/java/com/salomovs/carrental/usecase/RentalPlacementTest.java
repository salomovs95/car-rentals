package com.salomovs.carrental.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.salomovs.carrental.model.dto.RentalPlacementDto;
import com.salomovs.carrental.model.repository.CustomerRepository;
import com.salomovs.carrental.model.repository.RentalRepository;
import com.salomovs.carrental.model.repository.VehicleRepository;
import com.salomovs.carrental.service.RentalService;

@Sql(scripts="/seed.sql", executionPhase=ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest @Tag("UNIT_TESTS") @TestInstance(Lifecycle.PER_CLASS)
public class RentalPlacementTest {
  @Autowired
  private CustomerRepository crMock;

  @Autowired
  private VehicleRepository vrMock;

  @Autowired
  private RentalRepository rtrMock;
  private RentalService rtService;

  @BeforeAll
  void setup() {
    rtService = new RentalService(rtrMock);
  }

  @Test
  void PlaceRentalSuccess() {
    assertDoesNotThrow(()->{
      RentalPlacementDto dto = new RentalPlacementDto(
        crMock.findById(997).get(),
        vrMock.findById(999).get()
      );
      rtService.placeRental(dto);
    });
  }
}
