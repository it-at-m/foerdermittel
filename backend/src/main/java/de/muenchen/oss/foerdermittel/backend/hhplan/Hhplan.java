package de.muenchen.oss.foerdermittel.backend.hhplan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

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

    @Column(name = "hhj_jahr", nullable = false)
    @Id
    @Min(1900) @Max(2099) private BigDecimal hhjJahr;

    @Column(nullable = false)
    @NotBlank @Size(min = 0, max = 15) private String fipo;

}
