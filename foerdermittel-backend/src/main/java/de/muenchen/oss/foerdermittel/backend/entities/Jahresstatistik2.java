package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "fp_jahresstatistik2")
public class Jahresstatistik2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jss_jahr", nullable = false)
    private Jahresstatistik jssJahr;

    @Size(max = 60)
    @NotNull
    @Column(name = "foerderbereich", nullable = false, length = 60)
    private String foerderbereich;

    @NotNull
    @Column(name = "fb", nullable = false, precision = 2)
    private BigDecimal fb;

    @NotNull
    @Column(name = "gruppe", nullable = false, precision = 1)
    private BigDecimal gruppe;

    @Column(name = "anzahl_abrufe", precision = 10)
    private BigDecimal anzahlAbrufe;

    @Column(name = "anzahl_vn", precision = 10)
    private BigDecimal anzahlVn;

    @Column(name = "anzahl_bewilligungen", precision = 10)
    private BigDecimal anzahlBewilligungen;

    @Column(name = "vngeskosten", precision = 12, scale = 2)
    private BigDecimal vngeskosten;

    @Column(name = "anzahl_erst", precision = 10)
    private BigDecimal anzahlErst;

    @Column(name = "a_su_z_erst", precision = 12, scale = 2)
    private BigDecimal aSuZErst;

    @Column(name = "a_su_k_erst", precision = 12, scale = 2)
    private BigDecimal aSuKErst;

    @Column(name = "anzahl_folge", precision = 10)
    private BigDecimal anzahlFolge;

    @Column(name = "a_su_z_folge", precision = 12, scale = 2)
    private BigDecimal aSuZFolge;

    @Column(name = "a_su_k_folge", precision = 12, scale = 2)
    private BigDecimal aSuKFolge;

    @Column(name = "a_vor_su_z_gesamt", precision = 12, scale = 2)
    private BigDecimal aVorSuZGesamt;

    @Column(name = "a_vor_su_k_gesamt", precision = 12, scale = 2)
    private BigDecimal aVorSuKGesamt;

    @Column(name = "anzahl_unbed", precision = 10)
    private BigDecimal anzahlUnbed;


}