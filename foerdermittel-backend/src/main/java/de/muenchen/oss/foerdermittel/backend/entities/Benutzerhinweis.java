package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fp_benutzerhinweise")
public class Benutzerhinweis {
    @Id
    @Size(max = 30)
    @Column(name = "for_formsmodul", nullable = false, length = 30)
    private String forFormsmodul;

    @Column(name = "hinweis1", length = Integer.MAX_VALUE)
    private String hinweis1;

    @Column(name = "hinweis2", length = Integer.MAX_VALUE)
    private String hinweis2;

    @Column(name = "hinweis3", length = Integer.MAX_VALUE)
    private String hinweis3;

    @Size(max = 40)
    @Column(name = "menuetop", length = 40)
    private String menuetop;

    @Size(max = 40)
    @Column(name = "menuezeile", length = 40)
    private String menuezeile;


}