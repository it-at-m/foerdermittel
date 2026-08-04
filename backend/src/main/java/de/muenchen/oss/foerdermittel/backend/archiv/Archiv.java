package de.muenchen.oss.foerdermittel.backend.archiv;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "archiv")
public class Archiv implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "pro_projnr",
            referencedColumnName = "projnr",
            nullable = false
    )
    @NotNull private Projekt projekt;

    @Column(name = "speicherdatum", nullable = false)
    @NotNull private LocalDate speicherDatum;

    @Column(name = "speicherakt", nullable = false)
    @NotNull private Boolean speicherAkt;

    @Column(name = "speicherrechnungen", nullable = false)
    @NotNull private Boolean speicherRechnungen;

    @Column(name = "mikrodatplan")
    @NotNull private LocalDate mikroDatPlan;

    @Column(name = "mikrodat")
    @NotNull private LocalDate mikroDat;

    @Column(name = "notizen")
    @NotNull private String notizen;

}
