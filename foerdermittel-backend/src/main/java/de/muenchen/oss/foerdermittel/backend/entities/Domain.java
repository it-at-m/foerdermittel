package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "fp_domains")
public class Domain {
    @EmbeddedId
    private DomainId id;

    @Size(max = 50)
    @NotNull
    @Column(name = "beschreibung", nullable = false, length = 50)
    private String beschreibung;


}