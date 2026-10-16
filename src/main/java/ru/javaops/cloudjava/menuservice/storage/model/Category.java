package ru.javaops.cloudjava.menuservice.storage.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.http.HttpStatus;
import ru.javaops.cloudjava.menuservice.exception.MenuServiceException;

public enum Category {
  BREAKFAST,
  LUNCH,
  DINNER,
  DRINKS,
  SNACKS,
  SALADS;

  @JsonCreator
  public static Category fromString(String value) {
    try {
      return Category.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new MenuServiceException("Failed to create Category from string: %s"
          .formatted(value), HttpStatus.BAD_REQUEST);
    }
  }
}
