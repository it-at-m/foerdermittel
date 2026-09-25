package de.muenchen.oss.foerdermittel.backend.hhplan;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class HhplanPrimaryKey implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "hhj_jahr")
    private BigDecimal hhjJahr;

    @Column(name = "pro_projnr")
    private String proProjnr;
}