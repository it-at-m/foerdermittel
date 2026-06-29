package de.muenchen.oss.foerdermittel.backend.common;

import jakarta.validation.ConstraintViolationException;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        Throwable root = ex.getMostSpecificCause();

        switch (root) {
            case ConstraintViolationException cve -> throw new ConflictException(cve.getMessage());
            case PropertyValueException pve ->
                    throw new BadRequestException(String.format("Missing required field '%s' for entity '%s'", pve.getPropertyName(), pve.getEntityName()));
            case DataException de -> throw new BadRequestException(String.format("Invalid field value: %s", de.getMessage()));
            default -> throw new InternalServerErrorException(ex.getMessage());
        }
    }

}
