package de.muenchen.oss.foerdermittel.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class required to use Spring JPA Auditing and
 * {@link de.muenchen.oss.foerdermittel.backend.common.AuditableEntity}.
 *
 * @see <a href=
 *      "https://docs.spring.io/spring-data/jpa/reference/auditing.html">https://docs.spring.io/spring-data/jpa/reference/auditing.html</a>
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "usernameAuditorAware", modifyOnCreate = false)
public class JPAAuditingConfiguration {
}
