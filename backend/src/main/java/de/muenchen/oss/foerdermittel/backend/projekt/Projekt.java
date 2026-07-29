package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.archiv.Archiv;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
    @NotNull
    @Size(min = 1, max = 7)
    private String projnr;  // angepasst von projektnr auf projnr, da im Mapper so verwendet

    @Column(name = "pname", nullable = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String pname;

    @Column(name = "pstrasse", nullable = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String pstrasse;

    @Column(name = "fob_fb", nullable = false, precision = 2)
    @NotNull
    private BigDecimal fob_fb;

    @OneToMany(
            mappedBy = "projekt",
            fetch = FetchType.LAZY
    )
    private List<Archiv> archiv;
}
