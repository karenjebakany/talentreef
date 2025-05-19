package com.talentreef.interviewquestions;

import com.talentreef.interviewquestions.takehome.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
    log.error("Resource not found: {}", ex.getMessage());
    List<String> errorList = Collections.singletonList(ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorList);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    List<String> errorList = ex
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .map((field) -> field.getField() + ": " + field.getDefaultMessage())
        .toList();
    log.error("Validation error: {}", Arrays.toString(errorList.toArray()));
    return ResponseEntity.badRequest().body(errorList);
  }

}
