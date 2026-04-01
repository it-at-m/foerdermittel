package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "fp_projekttermine", indexes = {@Index(name = "fp_projekttermine_pro_projnr_idx",
        columnList = "pro_projnr")})
public class ProjektTermin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pro_projnr", nullable = false)
    private Projekt proProjnr;

    @NotNull
    @Column(name = "termin", nullable = false)
    private LocalDate termin;

    @Size(max = 60)
    @Column(name = "zustaendig", length = 60)
    private String zustaendig;

    @Size(max = 30)
    @Column(name = "telefon", length = 30)
    private String telefon;

    @Column(name = "notizen", length = Integer.MAX_VALUE)
    private String notizen;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "ueberwachung", nullable = false)
    private Boolean ueberwachung;


}