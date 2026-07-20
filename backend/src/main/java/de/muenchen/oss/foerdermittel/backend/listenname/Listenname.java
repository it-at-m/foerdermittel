package de.muenchen.oss.foerdermittel.backend.listenname;

import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Stadtbezirksliste;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Listenname.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "listennamen")
public class Listenname implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @Id
    @Column(nullable = false, length = 3)
    @NotNull
    @Size(min = 1, max = 3)
    @Pattern(regexp = "^[A-Z0-9]{1,3}$")
    private String kurzbez;


    @Column(nullable = false, length = 200)
    @NotNull
    @Size(min = 1, max = 200)
    private String bezeichnung;


    @OneToMany(
            mappedBy = "listenName",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Stadtbezirksliste> stadtbezirkslisten = new ArrayList<>();

}