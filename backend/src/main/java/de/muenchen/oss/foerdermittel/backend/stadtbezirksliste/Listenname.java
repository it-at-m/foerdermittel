package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents a Listenname.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "listennamen")
public class Listenname implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false, length = 3)
    @NotNull @Size(min = 1, max = 3) @Pattern(regexp = "^[A-Z0-9]{1,3}$") private String kurzbez;

    @Column(nullable = false, length = 200)
    @NotNull @Size(min = 1, max = 200) private String bezeichnung;

    @OneToMany(
            mappedBy = "listenName",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @OrderBy("id.stadtbezirk ASC")
    private List<Stadtbezirksliste> stadtbezirkslisten = new ArrayList<>();

    public void updateStadtbezirke(final List<Stadtbezirksliste> listen, final String kurzBez) {
        stadtbezirkslisten.clear();

        listen.forEach(assignment -> {
            assignment.setListenName(this);
            assignment.setId(new StadtbezirkslistePrimaryKey(
                    kurzBez,
                    assignment.getStadtbezirk().getStadtbezirk()));
            stadtbezirkslisten.add(assignment);
        });
    }
}
