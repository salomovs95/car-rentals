package com.salomovs.carrental.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.carrental.annotation.ApiGetOperation;
import com.salomovs.carrental.annotation.ApiPatchOperation;
import com.salomovs.carrental.annotation.ApiPostOperation;
import com.salomovs.carrental.model.dto.NewVehicleDto;
import com.salomovs.carrental.model.dto.PageableResponse;
import com.salomovs.carrental.model.dto.UpdateVehicleDto;
import com.salomovs.carrental.model.entity.Vehicle;
import com.salomovs.carrental.service.VehicleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequestMapping("/vehicles") @RequiredArgsConstructor
public class VehicleController {
  private final VehicleService vehicleService;

  @PostMapping @ApiPostOperation(summary="Register a new vehicle")
  public ResponseEntity<Void> registerNewVehicle(@RequestBody @Valid NewVehicleDto body) {
    vehicleService.saveVehicle(body);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping @ApiGetOperation(summary="Retrieve a list of available vehicles")
  public ResponseEntity<PageableResponse<Vehicle>> listVehicles(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> limit) {
    Pageable req = PageRequest.of(page.orElse(0), limit.orElse(20));
    PageableResponse<Vehicle> res = vehicleService.retrieveVehicle(req);
    return ResponseEntity.status(HttpStatus.OK).body(res);
  }

  @GetMapping("/{vehicleId}") @ApiGetOperation(summary="Retrieve info 'bout a given vehicle'")
  public ResponseEntity<Vehicle> getVehicleInfo9(@PathVariable Integer vehicleId) {
    Vehicle vehicle = vehicleService.retrieveVehicleInfo(vehicleId);
    return ResponseEntity.status(HttpStatus.OK).body(vehicle);
  }

  @PatchMapping("/{vehicleId}") @ApiPatchOperation(summary="")
  public ResponseEntity<Void> updateVehicle(@PathVariable Integer vehicleId, @RequestBody UpdateVehicleDto body) {
    vehicleService.updateVehicle(vehicleId, body);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
