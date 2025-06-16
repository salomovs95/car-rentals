package com.salomovs.carrental.model.dto;

import java.util.Optional;

import jakarta.validation.constraints.Email;

public record UpdateCustomerDto(
  @Email(message="The field 'email' must conform to a valid email shape, eg. 'john_doe@email.com'")
  Optional<String> email,
  Optional<String> phone
) {}
