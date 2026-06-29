package de.muenchen.oss.foerdermittel.backend.common;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        Throwable cause = ex.getCause();

        return switch (cause) {
            case ConstraintViolationException cve ->
                    ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, cve.getMessage());
            case PropertyValueException pve ->
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, String.format("Missing required field '%s' for entity '%s'", pve.getPropertyName(), pve.getEntityName()));
            case DataException de ->
                    ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, String.format("Invalid field value: %s", de.getMessage()));
            case null, default -> ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        };
    }

}
