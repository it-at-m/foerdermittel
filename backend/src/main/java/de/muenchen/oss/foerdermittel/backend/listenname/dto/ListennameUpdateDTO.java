package de.muenchen.oss.foerdermittel.backend.listenname.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ListennameUpdateDTO {


    @NotNull
    @Size(min = 1, max = 200)
    private String bezeichnung;

}