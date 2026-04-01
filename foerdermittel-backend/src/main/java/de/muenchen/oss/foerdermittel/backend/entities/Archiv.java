package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "fp_archiv", indexes = {@Index(name = "fp_archiv_pro_projnr_idx",
        columnList = "pro_projnr")})
public class Archiv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_projnr")
    private Projekt proProjnr;

    @Column(name = "speicherdatum")
    private LocalDate speicherdatum;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "speicherakt", nullable = false)
    private Boolean speicherakt;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "speicherrechnungen", nullable = false)
    private Boolean speicherrechnungen;

    @Column(name = "mikrodatplan")
    private LocalDate mikrodatplan;

    @Column(name = "mikrodat")
    private LocalDate mikrodat;

    @Column(name = "notizen", length = Integer.MAX_VALUE)
    private String notizen;


}