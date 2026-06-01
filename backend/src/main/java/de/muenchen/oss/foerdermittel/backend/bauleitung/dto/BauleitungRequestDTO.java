package de.muenchen.oss.foerdermittel.backend.bauleitung.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a request DTO for creating or updating a Bauleitung.
 * <p>
 * It contains the necessary fields to be provided by the client.
 * </p>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BauleitungRequestDTO {

    // ========= //
    // Variables //
    // ========= //

    @NotNull
    private String bauleitung;

    @NotNull
    @Size(max = 200)
    private String bezeichnung;

}