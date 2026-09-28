package de.muenchen.oss.foerdermittel.backend.hhplan;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/// This class represents a Hhplan.
///
/// The entity's attributes are mapped to the corresponding database columns.
///
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hhplan")
public class Hhplan implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    //    @Column(name = "hhj_jahr", nullable = false)
    //    @Id
    //    @Min(1900) @Max(2099) private BigDecimal hhjJahr;

    @EmbeddedId
    private HhplanPrimaryKey id;

    @Column(nullable = false)
    @NotBlank @Size(min = 0, max = 15) private String fipo;

}
