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
@Table(name = "fp_stichwortbereiche")
public class Stichwortbereich {
    @Id
    @Size(max = 30)
    @Column(name = "bereich", nullable = false, length = 30)
    private String bereich;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @OneToMany(mappedBy = "stbBereich")
    private Set<Ablageindex> ablageIndizes = new LinkedHashSet<>();


}