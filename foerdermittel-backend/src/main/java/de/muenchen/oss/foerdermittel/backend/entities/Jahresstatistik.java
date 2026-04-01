package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "fp_jahresstatistik")
public class Jahresstatistik {
    @Id
    @Column(name = "jahr", nullable = false, precision = 4)
    private BigDecimal id;

    @Column(name = "jahresstatistik1_am")
    private LocalDate jahresstatistik1Am;

    @Size(max = 30)
    @Column(name = "jahresstatistik1_von", length = 30)
    private String jahresstatistik1Von;

    @Column(name = "jahresstatistik2_am")
    private LocalDate jahresstatistik2Am;

    @Size(max = 30)
    @Column(name = "jahresstatistik2_von", length = 30)
    private String jahresstatistik2Von;

    @Column(name = "jahresstatistik3_am")
    private LocalDate jahresstatistik3Am;

    @Size(max = 30)
    @Column(name = "jahresstatistik3_von", length = 30)
    private String jahresstatistik3Von;

    @Column(name = "notizen", length = Integer.MAX_VALUE)
    private String notizen;

    @OneToMany(mappedBy = "jssJahr")
    private Set<Jahresstatistik1> jahresstatistiken1 = new LinkedHashSet<>();

    @OneToMany(mappedBy = "jssJahr")
    private Set<Jahresstatistik2> jahresstatistiken2 = new LinkedHashSet<>();

    @OneToMany(mappedBy = "jssJahr")
    private Set<Jahresstatistik3> jahresstatistiken3 = new LinkedHashSet<>();


}