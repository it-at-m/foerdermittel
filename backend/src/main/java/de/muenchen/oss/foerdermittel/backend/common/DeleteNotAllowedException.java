package de.muenchen.oss.foerdermittel.backend.common;

import lombok.Getter;

@Getter
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class DeleteNotAllowedException extends RuntimeException {
    private final String targetClassName;
    private final String violatedClassName;

    public DeleteNotAllowedException(final Class<?> targetClass, final Class<?> violatedClass) {
        super(String.format("%s can't be deleted due to %s still being referenced.", targetClass.getSimpleName(), violatedClass.getSimpleName()));
        this.targetClassName = targetClass.getSimpleName();
        this.violatedClassName = violatedClass.getSimpleName();
    }
}
