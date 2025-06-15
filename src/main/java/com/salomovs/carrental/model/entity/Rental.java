package com.salomovs.carrental.model.entity;

import java.time.Duration;
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

import com.salomovs.carrental.model.enums.RentalConstant;

@Entity @Table(name="tbl_rentals")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Rental {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  
  private LocalDateTime rentAt;
  private LocalDateTime returnAt;

  private Integer amountToPay;

  @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="vehicle_id", referencedColumnName="id")
  private Vehicle vehicle;

  @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="customer_id", referencedColumnName="id")
  private Customer customer;

  public Rental(LocalDateTime rentAt, Customer customer, Vehicle vehicle) {
    this.amountToPay = 0;
    this.rentAt = rentAt;
    this.customer = customer;
    this.vehicle = vehicle;
  }

  public static Double calculateInterval(Rental rental) {
    if (rental.getReturnAt() == null) {
      return Duration.between(rental.getRentAt(), LocalDateTime.now()).getSeconds() / RentalConstant.HOUR_SECONDS;
    }
    return Duration.between(rental.getRentAt(), rental.getReturnAt()).getSeconds()/RentalConstant.HOUR_SECONDS;
  }
}
