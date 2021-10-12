package cz.bera.codium.controller.exception;

import javax.validation.constraints.NotNull;

public class NotFoundException extends RuntimeException {

  public NotFoundException(@NotNull String message) {
    super(message);
  }
}
