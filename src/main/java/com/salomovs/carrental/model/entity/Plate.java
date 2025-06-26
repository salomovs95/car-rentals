package com.salomovs.carrental.model.entity;

import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Plate {
  private String value;
  private String country;

  @Override
  public String toString() {
    String data = "{ code:%s, country:%d }";
    return String.format(data, value, country);
  }
}
