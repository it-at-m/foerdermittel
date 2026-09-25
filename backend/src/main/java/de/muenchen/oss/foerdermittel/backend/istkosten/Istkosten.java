package de.muenchen.oss.foerdermittel.backend.istkosten;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.istkosten.IstkostenPrimaryKey;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Listenname;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projektistkosten")
public class Istkosten implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @EmbeddedId
    private IstkostenPrimaryKey id;


    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("projnr")
    @JoinColumn(
            name = "pro_projnr",
            referencedColumnName = "projnr",
            nullable = false
    )
    @NotNull private Projekt projekt;


    @NotNull
    @Min(0)
    @Digits(integer=12, fraction=0)
    @Column(name = "istkosten", nullable = false)
    private BigDecimal istkosten;




}
