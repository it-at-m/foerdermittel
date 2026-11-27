package de.muenchen.oss.foerdermittel.backend.bewilligung;

import de.muenchen.oss.foerdermittel.backend.antrag.Antrag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bewilligungen")
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class Bewilligung implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ant_id", referencedColumnName = "id")
    private Antrag antrag;

    @Column(name = "pro_projnr", nullable = false)
    private String projnr;

    @Column(precision = 8, scale = 2, nullable = false)
    @Digits(integer = 6, fraction = 2) private BigDecimal afsatz;

    @Column(precision = 8, scale = 2, nullable = false)
    @Digits(integer = 6, fraction = 2) private BigDecimal bfsatz;

    @Column(precision = 10, scale = 0)
    @Digits(integer = 10, fraction = 0) private BigDecimal bzwfkosten;

    @Column(precision = 10, scale = 0)
    @Digits(integer = 10, fraction = 0) private BigDecimal geszuwendungen;

    @Column(precision = 10, scale = 0)
    @Digits(integer = 10, fraction = 0) private BigDecimal geskonnex;

    @Column(name = "anlagedatum", nullable = false, updatable = false, insertable = false)
    private LocalDateTime anlagedatum;

    @Column(nullable = false, updatable = false)
    @NotNull @Size(max = 30) private String anlagevon;

    //    The reference on projnr as well as this method to extract the projnr from antrag is necessary to mirror the database's schema since
    //    the table bewilligungen has an extra column for projnr. Changes to the schema would also make changes to a number of database views necessary.
    @PrePersist
    @PreUpdate
    private void setProjnrFromAntrag() {
        if (antrag == null
                || antrag.getProjekt() == null
                || antrag.getProjekt().getProjnr() == null) {
            throw new IllegalStateException(
                    "Eine Bewilligung benötigt einen Antrag mit Projekt und Projektnummer");
        }

        this.projnr = antrag.getProjekt().getProjnr();
    }

}
