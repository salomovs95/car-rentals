package com.salomovs.carrental.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salomovs.carrental.exception.VehicleNotFoundException;
import com.salomovs.carrental.model.dto.NewVehicleDto;
import com.salomovs.carrental.model.dto.PageableResponse;
import com.salomovs.carrental.model.dto.UpdateVehicleDto;
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

  public void updateVehicle(Integer vehicleId, UpdateVehicleDto dto) {
    Vehicle vehicle = vehicleRepository.findById(vehicleId)
                                       .orElseThrow(VehicleNotFoundException::new);

    vehicle.setHourPrice(dto.hourlyPrice().orElse(vehicle.getHourPrice()));
    vehicle.setDailyPrice(dto.dailyPrice().orElse(vehicle.getDailyPrice()));

    vehicleRepository.save(vehicle);
  }

  public PageableResponse<Vehicle> retrieveVehicle(Pageable page) {
    Page<Vehicle> data = vehicleRepository.findAll(page);
    return new PageableResponse<>(data.hasNext(), data.hasPrevious(), data.getContent());
  }

  public Vehicle retrieveVehicleInfo(Integer vehicleId) {
    Vehicle vehicle = vehicleRepository.findById(vehicleId)
                                       .orElseThrow(VehicleNotFoundException::new);
    return vehicle;
  }
}
