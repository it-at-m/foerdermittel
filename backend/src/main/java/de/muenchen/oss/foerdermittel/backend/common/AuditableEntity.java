package de.muenchen.oss.foerdermittel.backend.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * This class represents an auditable entity using Spring JPA Auditing
 * and allows storage of creation and modification timestamps and usernames.
 *
 * <p>
 * <strong>Note:</strong> A
 * {@link de.muenchen.oss.foerdermittel.backend.configuration.JPAAuditingConfiguration} is required
 * in
 * order to function correctly.
 * </p>
 *
 * @see <a href=
 *      "https://docs.spring.io/spring-data/jpa/reference/auditing.html">https://docs.spring.io/spring-data/jpa/reference/auditing.html</a>
 */
@MappedSuperclass
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(nullable = false)
    private String anlagevon;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime anlagedatum;

    @LastModifiedBy
    private String aenderungvon;

    @LastModifiedDate
    private LocalDateTime aenderungsdatum;

}
