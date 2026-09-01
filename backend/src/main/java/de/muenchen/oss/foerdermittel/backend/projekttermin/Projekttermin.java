package de.muenchen.oss.foerdermittel.backend.projekttermin;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This class represents a Termine.
 * <p>
 * The entity's attributes are mapped to the corresponding database columns.
 * </p>
 */
@SuppressWarnings("PMD.LinguisticNaming")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projekttermine")
public class Projekttermin implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "pro_projnr",
           referencedColumnName = "projnr",
            nullable = false)
  @NotNull private Projekt projekt;

    @Column(name = "termin")
    private LocalDate termin;

    @Column(name = "zustaendig")
    @NotNull @Size(min = 1, max = 60) String zustaendig;

    @Column(name = "ueberwachung")
    @NotNull boolean ueberwachung;

    @Column(name = "telefon")
    @NotNull @Size(min = 1, max = 30) String telefon;

    @Column(name = "notizen")
    private String notizen;








}

