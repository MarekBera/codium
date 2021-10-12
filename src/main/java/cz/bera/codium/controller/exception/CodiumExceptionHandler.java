package cz.bera.codium.controller.exception;

import cz.bera.codium.controller.model.ErrorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CodiumExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request
  ) {

    final List<String> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }

    final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

    return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getHttpStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request
  ) {

    final String error = ex.getParameterName() + " parameter is missing!";
    final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

    return new ResponseEntity<>(errorDetails, new HttpHeaders(), errorDetails.getHttpStatus());
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request
  ) {

    final Throwable mostSpecificCause = ex.getMostSpecificCause();
    final ErrorDetails errorDetails = new ErrorDetails(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST,
        mostSpecificCause.getClass().getName(),
        mostSpecificCause.getMessage()
    );
    return new ResponseEntity<>(errorDetails, new HttpHeaders(), errorDetails.getHttpStatus());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolation(
      ConstraintViolationException ex,
      WebRequest request
  ) {

    final List<String> errors = new ArrayList<>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
    }

    final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

    return new ResponseEntity<>(errorDetails, new HttpHeaders(), errorDetails.getHttpStatus());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex,
      WebRequest request
  ) {

    final String error = ex.getName() + " should by of type " + ex.getRequiredType().getName();
    final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

    return new ResponseEntity<>(errorDetails, new HttpHeaders(), errorDetails.getHttpStatus());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> NotFoundException(NotFoundException ex) {
    final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.NOT_FOUND, ex.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, new HttpHeaders(), errorDetails.getHttpStatus());
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<Object> NotFoundException(ConflictException ex) {
    final ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), HttpStatus.CONFLICT, ex.getLocalizedMessage());

    return new ResponseEntity<>(errorDetails, new HttpHeaders(), errorDetails.getHttpStatus());
  }

}
