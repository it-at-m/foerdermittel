package de.muenchen.oss.foerdermittel.backend.projekt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projekte")
public class Projekt {

    @Id
    @Column(name = "projnr", length = 7)
    private String projnr;

    @Column(name = "fob_fb", nullable = false)
    private BigDecimal fobFb;

    @Column(name = "kur_kurzbez", nullable = false, length = 3)
    private String kurKurzbez;

    @Column(name = "uas_ua", length = 2)
    private String uasUa;

    @Column(name = "jahr", length = 2)
    private String jahr;

    @Column(name = "lfdnr1", length = 1)
    private String lfdnr1;

    @Column(name = "lfdnr2", length = 2)
    private String lfdnr2;

    @Column(name = "pname", length = 100)
    private String pname;

    @Column(name = "pstrasse", length = 100)
    private String pstrasse;
}
