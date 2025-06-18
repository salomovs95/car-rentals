package com.salomovs.carrental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.carrental.annotation.ApiPostOperation;
import com.salomovs.carrental.model.dto.NewVehicleDto;
import com.salomovs.carrental.service.VehicleService;

import lombok.RequiredArgsConstructor;

@RestController @RequestMapping("/vehicles") @RequiredArgsConstructor
public class VehicleController {
  private final VehicleService vehicleService;

  @PostMapping @ApiPostOperation(summary="Register a new vehicle")
  public ResponseEntity<Void> registerNewVehicle(@RequestBody NewVehicleDto body) {
    vehicleService.saveVehicle(body);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
