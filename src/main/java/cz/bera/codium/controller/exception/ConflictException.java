package cz.bera.codium.controller.exception;

import javax.validation.constraints.NotNull;

public class ConflictException extends RuntimeException {

  public ConflictException(@NotNull String message) {
    super(message);
  }
}
