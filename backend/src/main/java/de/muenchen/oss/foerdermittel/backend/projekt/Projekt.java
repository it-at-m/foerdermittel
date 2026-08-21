package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.archiv.Archiv;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projekte")
public class Projekt implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "projnr", nullable = false)
    @NotNull @Size(min = 1, max = 7) private String projnr; // angepasst von projektnr auf projnr, da im Mapper so verwendet

    @Column(name = "pname", nullable = false)
    @NotNull @Size(min = 1, max = 100) private String pname;

    @Column(name = "pstrasse", nullable = false)
    @NotNull @Size(min = 1, max = 100) private String pstrasse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fob_fb",
            referencedColumnName = "fb",
            nullable = false
    )
    private Foerderbereich foerderbereich;

}
