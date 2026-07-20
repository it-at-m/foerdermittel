package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StadtbezirkslisteResponseDTO {

    private String listenname;

    private BigDecimal stadtbezirk;

    private String bezeichnung;

}