package com.salomovs.carrental.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name="tbl_rentals")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Rental {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  private LocalDateTime rentAt;
  private LocalDateTime returnAt;

  @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="vehicle_id", referencedColumnName="id")
  private Vehicle vehicle;

  @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="customer_id", referencedColumnName="id")
  private Customer customer;

  public Rental(LocalDateTime rentAt, Customer customer, Vehicle vehicle) {
    this.rentAt = rentAt;
    this.customer = customer;
    this.vehicle = vehicle;
  }

  @Override
  public String toString() {
    String data = "{ id:%d, rent:%s, return:%s, customer:%s, vehicle:%s }";
    return String.format(data, id, rentAt, returnAt, customer, vehicle);
  }
}
