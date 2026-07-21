package de.muenchen.oss.foerdermittel.backend.archiv;

import de.muenchen.oss.foerdermittel.backend.projekte.Projekte;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "archiv")
public class Archiv {

    // ========= //
    // Variables //
    // ========= //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pro_projnr", referencedColumnName = "projnr")
    @NotNull
    private Projekte projekte;

    @Column(name = "speicherdatum", nullable = false)
    @NotNull
    private LocalDate speicherDatum;

    @Column(name = "speicherakt", nullable = false)
    @NotNull
    private Boolean speicherAkt;

    @Column(name = "speicherrechnungen", nullable = false)
    @NotNull
    private Boolean speicherRechnungen;

    @Column(name = "mikrodatplan")
    @NotNull
    private LocalDate mikroDatPlan;

    @Column(name = "mikrodat")
    @NotNull
    private LocalDate mikroDat;

    @Column(name = "notizen")
    @NotNull
    private String notizen;

}
