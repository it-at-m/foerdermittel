package de.muenchen.oss.foerdermittel.backend.hhjahr;

import de.muenchen.oss.foerdermittel.backend.hhplan.Hhplan;
import de.muenchen.oss.foerdermittel.backend.hhplan.HhplanPrimaryKey;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Stadtbezirksliste;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hhjahre")
public class Hhjahr implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Id
    @Column(name = "hhjahr", nullable = false)
    @NotNull @Min(0) @Digits(integer = 4, fraction = 0) private BigDecimal hhjahr;

    @Column(name = "hhbezeichnung")
    @NotNull @Size(min = 1, max = 200) private String hhbezeichnung;

    @Column(name = "notizen")
    private String notizen;

    @OneToMany(mappedBy = "hhjJahr", fetch = FetchType.EAGER)
    private List<Hhplan> hhplanlisten = new ArrayList<>();
}
