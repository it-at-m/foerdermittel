package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "fp_protokoll")
public class Protokoll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false)
    private Long id;

    @Column(name = "log_date")
    private LocalDate logDate;

    @Size(max = 100)
    @Column(name = "log_module", length = 100)
    private String logModule;

    @Size(max = 30)
    @Column(name = "log_gui_user", length = 30)
    private String logGuiUser;

    @Column(name = "log_message", length = Integer.MAX_VALUE)
    private String logMessage;

    @Column(name = "log_error_message", length = Integer.MAX_VALUE)
    private String logErrorMessage;


}