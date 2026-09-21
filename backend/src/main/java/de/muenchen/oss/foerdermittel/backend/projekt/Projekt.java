package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.Siedlungsgebiet;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Listenname;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Stadtbezirksliste;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(name = "jahr", nullable = false)
    @NotNull private String jahr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fob_fb",
            referencedColumnName = "fb",
            nullable = false
    )
    private Foerderbereich foerderbereich;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "bez_stadtbezirk",
            referencedColumnName = "stadtbezirk",
            nullable = false
    )
    private Stadtbezirk stadtbezirk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "uas_ua",
            referencedColumnName = "ua",
            nullable = false
    )
    private Unterabschnitt unterabschnitt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "kur_kurzbez",
            referencedColumnName = "kurzbez",
            nullable = false
    )
    private Kurzbezeichnung kurzbezeichnung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sgt_siedlungsgebiet",
            referencedColumnName = "siedlungsgebiet",
            nullable = false
    )
    private Siedlungsgebiet siedlungsgebiet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "bpg_bauprogramm",
            referencedColumnName = "bauprogramm",
            nullable = false
    )
    private Bauprogramm bauprogramm;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "krisofp", columnDefinition = "projekte_krisofp")
    private Krisofp krisofp;


}
