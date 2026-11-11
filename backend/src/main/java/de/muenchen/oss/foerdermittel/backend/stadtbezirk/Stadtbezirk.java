package de.muenchen.oss.foerdermittel.backend.stadtbezirk;

import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Stadtbezirksliste;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

/// This class represents a Stadtbezirk.
///
/// The entity's attributes are mapped to the corresponding database columns.
///
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stadtbezirke")
public class Stadtbezirk implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Column(nullable = false)
    @Id
    @NotNull @Min(0) @Max(99) private BigDecimal stadtbezirk;

    @Column(nullable = false)
    @NotBlank @Size(min = 1, max = 200) private String bezeichnung;

    @OneToMany(mappedBy = "stadtbezirk", fetch = FetchType.EAGER)
    private List<Stadtbezirksliste> stadtbezirkslisten = new ArrayList<>();

}
