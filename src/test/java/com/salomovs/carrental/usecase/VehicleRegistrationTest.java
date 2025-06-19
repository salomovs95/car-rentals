package com.salomovs.carrental.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.salomovs.carrental.model.dto.NewVehicleDto;
import com.salomovs.carrental.model.entity.Plate;
import com.salomovs.carrental.model.entity.Vehicle;
import com.salomovs.carrental.model.repository.VehicleRepository;
import com.salomovs.carrental.service.VehicleService;

@SpringBootTest @Tag("UNIT_TESTS") @TestInstance(Lifecycle.PER_CLASS)
public class VehicleRegistrationTest {
  @MockitoBean
  private VehicleRepository vhrMock;
  private VehicleService vehicleService;

  @BeforeAll
  void setupOnce() {
    vehicleService = new VehicleService(vhrMock);
  }

  @Test
  void RegisterVehicleSuccessfull() {
    when(vhrMock.save(any(Vehicle.class)))
      .thenReturn(new Vehicle(LocalDate.now().getYear(), "Camaro SS 1962", "Chevrollet", 2347, 52328, new Plate("BG12A0D", "USA")));
    
    NewVehicleDto dto = new NewVehicleDto("Camaro SS 1962", "Chevrollet", "BG12A0D", "USA", 2347, 52328);
    assertDoesNotThrow(()->vehicleService.saveVehicle(dto));
  }
}
