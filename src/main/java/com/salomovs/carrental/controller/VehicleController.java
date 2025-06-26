package com.salomovs.carrental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.carrental.annotation.ApiPatchOperation;
import com.salomovs.carrental.annotation.ApiPostOperation;
import com.salomovs.carrental.model.dto.NewVehicleDto;
import com.salomovs.carrental.model.dto.UpdateVehicleDto;
import com.salomovs.carrental.service.VehicleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequestMapping("/vehicles") @RequiredArgsConstructor
public class VehicleController {
  private final VehicleService vehicleService;

  @PostMapping @ApiPostOperation(summary="Register a new vehicle")
  public ResponseEntity<Void> registerNewVehicle(@RequestBody @Valid NewVehicleDto body) {
    vehicleService.saveVehicle(body);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PatchMapping("/{vehicleId}") @ApiPatchOperation(summary="")
  public ResponseEntity<Void> updateVehicle(@PathVariable Integer vehicleId, @RequestBody UpdateVehicleDto body) {
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
