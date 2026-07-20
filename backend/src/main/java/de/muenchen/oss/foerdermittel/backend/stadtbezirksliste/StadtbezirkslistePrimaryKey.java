package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StadtbezirkslistePrimaryKey implements Serializable {


    @Column(name = "lna_kurzbez")
    private String listenname;


    @Column(name = "bez_stadtbezirk")
    private BigDecimal stadtbezirk;

}