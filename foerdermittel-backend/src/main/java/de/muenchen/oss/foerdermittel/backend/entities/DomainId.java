package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class DomainId implements Serializable {
    private static final long serialVersionUID = -2479502788620662097L;
    @Size(max = 30)
    @NotNull
    @Column(name = "tabelle", nullable = false, length = 30)
    private String tabelle;

    @Size(max = 30)
    @NotNull
    @Column(name = "spalte", nullable = false, length = 30)
    private String spalte;

    @Size(max = 3)
    @NotNull
    @Column(name = "wert", nullable = false, length = 3)
    private String wert;


}