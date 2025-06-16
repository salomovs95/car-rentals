package com.salomovs.carrental.model.dto;

import java.util.List;

public record PageableResponse<T>(
  boolean hasNext,
  boolean hasPrevious,
  List<T> content
) { }
