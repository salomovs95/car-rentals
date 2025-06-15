package com.salomovs.carrental.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NewCustomerDto (
  @NotBlank(message="The field 'taxId' field must not be an empty string nor a null value")
  String taxId,
  
  @NotBlank(message="The field 'fullName' must not be an empty string nor a null value")
  String fullName,
  
  @NotBlank(message="The field 'phone' must not be an empty string nor a null value")
  String phone,
  
  @NotBlank(message="The field 'email' must not be an empty string nor a null value")
  @Email(message="The field 'email' must conform to a valid email shape, eg. 'john_doe@carrental.com'")
  String email
) {}
