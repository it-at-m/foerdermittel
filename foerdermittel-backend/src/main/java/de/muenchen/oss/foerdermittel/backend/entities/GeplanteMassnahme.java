package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "fp_geplantemassnahmen")
public class GeplanteMassnahme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kur_kurzbez", nullable = false)
    private Kurzbezeichnung kurKurzbez;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bez_stadtbezirk")
    private Stadtbezirk bezStadtbezirk;

    @Size(max = 50)
    @Column(name = "strasse", length = 50)
    private String strasse;

    @Size(max = 70)
    @Column(name = "projekt", length = 70)
    private String projekt;

    @Column(name = "baubeginn", precision = 4)
    private BigDecimal baubeginn;

    @Column(name = "angelegt", precision = 4)
    private BigDecimal angelegt;

    @Size(max = 10)
    @Column(name = "plannr", length = 10)
    private String plannr;


}