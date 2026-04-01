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
@Table(name = "fp_listennamen")
public class Listenname {
    @Id
    @Size(max = 3)
    @Column(name = "kurzbez", nullable = false, length = 3)
    private String kurzbez;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @OneToMany(mappedBy = "lnaKurzbez")
    private Set<Stadtbezirksliste> stadtbezirkslisten = new LinkedHashSet<>();


}