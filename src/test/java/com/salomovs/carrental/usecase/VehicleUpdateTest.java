package com.salomovs.carrental.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.salomovs.carrental.exception.VehicleNotFoundException;
import com.salomovs.carrental.model.dto.UpdateVehicleDto;
import com.salomovs.carrental.model.repository.VehicleRepository;
import com.salomovs.carrental.service.VehicleService;

@Sql(scripts="/seed.sql", executionPhase=ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest @Tag("UNIT_TESTS") @TestInstance(Lifecycle.PER_CLASS)
public class VehicleUpdateTest {
  @Autowired
  private VehicleRepository vrMock;
  private VehicleService vehicleService;

  @BeforeAll
  void setupOnce() {
    vehicleService = new VehicleService(vrMock);
  }

  @Test
  void UpdateVehicleSuccessfulll() {
    UpdateVehicleDto dto = new UpdateVehicleDto(Optional.of(1230), Optional.of(16060));
    assertDoesNotThrow(()->vehicleService.updateVehicle(999, dto));
  }

  @Test
  void UpdateVehicleFailVehicleNotFoundd() {
    UpdateVehicleDto dto = new UpdateVehicleDto(Optional.empty(), Optional.empty());
    assertThrows(VehicleNotFoundException.class,
                 ()->vehicleService.updateVehicle(990, dto));
  }
}
