package com.salomovs.carrental.service;

import org.springframework.stereotype.Service;

import com.salomovs.carrental.model.dto.NewVehicleDto;
import com.salomovs.carrental.model.entity.Plate;
import com.salomovs.carrental.model.entity.Vehicle;
import com.salomovs.carrental.model.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service @RequiredArgsConstructor @Slf4j
public class VehicleService {
  private final VehicleRepository vehicleRepository;

  public void saveVehicle(NewVehicleDto dto) {
    Plate plate = new Plate(dto.plateCode(), dto.country());
    Vehicle vehicle = new Vehicle(null, dto.model(), dto.brand(), dto.dailyPrice(), dto.hourlyPrice(), plate);

    int vehicleId = vehicleRepository.save(vehicle).getId();
    log.debug(String.format("Successfully registered vehicle under ID: %d", vehicleId));
  }
}
