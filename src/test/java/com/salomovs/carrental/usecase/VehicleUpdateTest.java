package com.salomovs.carrental.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.salomovs.carrental.exception.VehicleNotFoundException;
import com.salomovs.carrental.model.dto.UpdateVehicleDto;
import com.salomovs.carrental.model.entity.Plate;
import com.salomovs.carrental.model.entity.Vehicle;
import com.salomovs.carrental.model.repository.VehicleRepository;
import com.salomovs.carrental.service.VehicleService;

@SpringBootTest @Tag("UNIT_TESTS") @TestInstance(Lifecycle.PER_CLASS)
public class VehicleUpdateTest {
  @MockitoBean
  private VehicleRepository vrMock;
  private VehicleService vehicleService;

  @BeforeAll
  void setupOnce() {
    vehicleService = new VehicleService(vrMock);
  }

  @Test
  void UpdateVehicleSuccessfulll() {
    UpdateVehicleDto dto = new UpdateVehicleDto(Optional.of(1230), Optional.of(16060));
    when(vrMock.findById(anyInt())).thenReturn(Optional.of(
      new Vehicle(999, "model", "brand", 10900, 5100, new Plate("code", "country"))
    ));

    assertDoesNotThrow(()->vehicleService.updateVehicle(999, dto));
  }

  @Test
  void UpdateVehicleFailVehicleNotFoundd() {
    UpdateVehicleDto dto = null;
    when(vrMock.findById(anyInt())).thenReturn(Optional.empty());

    assertThrows(VehicleNotFoundException.class,
                 ()->vehicleService.updateVehicle(999, dto));
  }
}
