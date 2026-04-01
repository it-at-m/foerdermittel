package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "fp_publikationen")
public class Publikation {
    @Id
    @Size(max = 1)
    @Column(name = "kurzform", nullable = false, length = 1)
    private String kurzform;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @OneToMany(mappedBy = "pubKurzform")
    private Set<EuInformationen> euInformationen = new LinkedHashSet<>();


}