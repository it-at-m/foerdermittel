package de.muenchen.oss.foerdermittel.backend.domain.krankenhaus;

import jakarta.validation.constraints.NotEmpty;
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
public class KrankenhausCreateDto {
    @NotEmpty
    @Size(max = 1)
    private String krhname;


    @Size(max = 200)
    @NotNull
    private String bezeichnung;

    @NotNull
    private LocalDateTime datum;


    @NotNull
    private String datum1;
}
