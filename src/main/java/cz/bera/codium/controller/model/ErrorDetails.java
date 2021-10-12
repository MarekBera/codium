package cz.bera.codium.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorDetails {

  private LocalDateTime timestamp;
  private HttpStatus httpStatus;
  private String message;
  private List<String> errors;

  public ErrorDetails(LocalDateTime timestamp, HttpStatus httpStatus, String message, String error) {
    super();
    this.timestamp = timestamp;
    this.httpStatus = httpStatus;
    this.message = message;
    errors = List.of(error);
  }

  public ErrorDetails(LocalDateTime timestamp, HttpStatus httpStatus, String message) {
    super();
    this.timestamp = timestamp;
    this.httpStatus = httpStatus;
    this.message = message;
    errors = new ArrayList<>();
  }
}
