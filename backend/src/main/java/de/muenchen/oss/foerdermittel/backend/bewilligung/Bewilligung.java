package de.muenchen.oss.foerdermittel.backend.bewilligung;

import de.muenchen.oss.foerdermittel.backend.antrag.Antrag;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bewilligungen")
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

}
