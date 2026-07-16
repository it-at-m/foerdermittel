package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public record StadtbezirkslistePrimaryKey(BigDecimal bezStadtbezirk,
                                          String lnaKurzbez) {


}
