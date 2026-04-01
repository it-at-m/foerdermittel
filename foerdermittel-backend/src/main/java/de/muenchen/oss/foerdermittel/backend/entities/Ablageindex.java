package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "fp_ablageindexe")
public class Ablageindex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stb_bereich", nullable = false)
    private Stichwortbereich stbBereich;

    @Column(name = "nr", precision = 4)
    private BigDecimal nr;

    @Size(max = 80)
    @Column(name = "wort", length = 80)
    private String wort;

    @Size(max = 400)
    @Column(name = "stichworte", length = 400)
    private String stichworte;


}