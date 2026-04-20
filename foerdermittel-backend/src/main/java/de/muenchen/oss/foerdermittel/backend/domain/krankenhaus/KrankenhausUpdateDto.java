package de.muenchen.oss.foerdermittel.backend.domain.krankenhaus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class KrankenhausUpdateDto {
    @Size(max = 200)
    @NotNull
    private String bezeichnung;
}
