package de.muenchen.oss.foerdermittel.backend.foerderbereich;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "foerderbereiche")
public class Foerderbereich implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Id
    @Column(nullable = false)
    @Min(0) @Max(99) private BigDecimal fb;

    @Column(nullable = false)
    @NotNull @Size(min = 1, max = 200) private String bezeichnung;

    @Column(name = "finanzausgleich", nullable = false)
    @NotNull Boolean finanzausgleich;

    @Column(name = "jahresstatistik", nullable = false)
    @NotNull Boolean jahresstatistik;

    @Column(name = "kindergarten", nullable = false)
    @NotNull Boolean kindergarten;

    @Column(name = "nicht_relevant", nullable = false)
    @NotNull Boolean nichtRelevant;
}
