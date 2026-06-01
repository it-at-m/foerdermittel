package de.muenchen.oss.foerdermittel.backend.bauleitung;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * This class represents a Bauleitung.
 * <p>
 * The entity's attributes are mapped to the corresponding database columns.
 * </p>
 */

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "bauleitungen")
public class Bauleitung {

    // ========= //
    // Variables //
    // ========= //

    @Id
    @Column(nullable = false, precision = 2)
    @NotNull
    @Min(1)
    @Max(99)
    private String bauleitung;





    @Column(nullable = false, length = 200)
    @NotNull
    @Size(max = 200)
    private String bezeichnung;





}