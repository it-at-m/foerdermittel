package de.muenchen.oss.foerdermittel.backend.common;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        log.warn("Data integrity violation: ", ex);
        final Throwable cause = ex.getCause();

        return switch (cause) {
        case ConstraintViolationException ignored ->
            ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Entity violates a database constraint");
        case PropertyValueException pve ->
            ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                    String.format("Missing required field '%s' for entity '%s'", pve.getPropertyName(), pve.getEntityName()));
        case DataException ignored ->
            ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid field value");
        case null, default -> ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected data integrity violation");
        };
    }

    @ExceptionHandler(DeleteNotAllowedException.class)
    public ProblemDetail handleDeleteNotAllowed(
            final DeleteNotAllowedException exception) {

        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
        problemDetail.setProperty("targetModelName", exception.getTargetClassName());
        problemDetail.setProperty("violatedModelName", exception.getViolatedClassName());

        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFound(
            final NotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

}
