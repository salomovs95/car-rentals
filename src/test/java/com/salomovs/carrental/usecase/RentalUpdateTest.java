package com.salomovs.carrental.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.salomovs.carrental.exception.RentalNotFoundException;
import com.salomovs.carrental.model.repository.RentalRepository;
import com.salomovs.carrental.service.RentalService;

@Sql(scripts="/seed.sql", executionPhase=ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest @Tag("UNIT_TESTS") @TestInstance(Lifecycle.PER_CLASS)
public class RentalUpdateTest {
  @Autowired
  private RentalRepository rrMock;
  private RentalService rrService;

  @BeforeAll
  void setup() {
    rrService = new RentalService(rrMock);
  }

  void ReturnVehicleSuccess() {
    int rentalId = 899;
    assertDoesNotThrow(()->rrService.returnVehicle(rentalId));
  }

  void ReturnVehicleFail() {
    int rentalId = 1000;
    assertThrows(RentalNotFoundException.class, ()->rrService.returnVehicle(rentalId));
  }
}
