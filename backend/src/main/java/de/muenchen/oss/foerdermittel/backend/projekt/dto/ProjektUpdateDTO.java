package de.muenchen.oss.foerdermittel.backend.projekt.dto;


import jakarta.validation.constraints.Size;

public record ProjektUpdateDTO(
        @Size(min = 1, max = 100)  String pname,
        @Size(min = 1, max = 100)  String pstrasse
)
{
}
