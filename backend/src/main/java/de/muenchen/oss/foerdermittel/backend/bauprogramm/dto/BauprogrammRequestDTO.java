package de.muenchen.oss.foerdermittel.backend.bauprogramm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a request DTO for creating or updating a Bauprogramm.
 * <p>
 * It contains the necessary fields to be provided by the client.
 * </p>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BauprogrammRequestDTO {

    // ========= //
    // Variables //
    // ========= //

    @NotNull
//    private Integer bauprogramm;
    private Integer id;
    @NotNull
    @Size(max = 200)
    private String bezeichnung;

}
