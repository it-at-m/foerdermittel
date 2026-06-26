package de.muenchen.oss.foerdermittel.backend.common;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.TestConstants;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DataJpaTest
public class InsertAndUpdateRepositoryImplIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    private InsertAndUpdateRepositoryImpl<Bauprogramm, BigDecimal> repository;

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(TestConstants.TESTCONTAINERS_POSTGRES_IMAGE));

    @BeforeEach
    void setUp() {
        JpaEntityInformation<Bauprogramm, BigDecimal> info = new JpaMetamodelEntityInformation<>(
                Bauprogramm.class,
                entityManager.getMetamodel(),
                entityManager.getEntityManagerFactory().getPersistenceUnitUtil());

        repository = new InsertAndUpdateRepositoryImpl<>(entityManager, info);
    }

    @Nested
    class Insert {

        @Test
        void givenNoEntity_thenInsertsEntity() {
            // given
            final Bauprogramm entity = new Bauprogramm(BigDecimal.valueOf(10), "Test");

            // when
            final Bauprogramm saved = repository.insert(entity);

            // then
            assertThat(saved).isNotNull();
            assertThat(saved).isEqualTo(entity);
        }

        @Test
        void givenEntityExists_thenThrowException() {
            // given
            final BigDecimal id = BigDecimal.valueOf(10);
            final Bauprogramm entity = new Bauprogramm(id, "Test");
            final Bauprogramm entity2 = new Bauprogramm(entity.getBauprogramm(), entity.getBezeichnung());
            repository.insert(entity, true);

            // when
            final Exception exception = Assertions.assertThrows(EntityExistsException.class, () -> repository.insert(entity2));

            // then
            assertThat(exception.getMessage()).isEqualTo(String.format(
                    "A different object with the same identifier value was already associated with this persistence context for entity [%s with id '%s']",
                    entity.getClass().getCanonicalName(), id.intValueExact()));
        }

    }

    @Nested
    class Update {

        @Test
        void givenEntityExists_thenUpdateEntity() {
            // given
            final BigDecimal id = BigDecimal.valueOf(10);
            final Bauprogramm entity = new Bauprogramm(id, "Test");
            final Bauprogramm updateEntity = new Bauprogramm(id, "Test (updated)");
            repository.insert(entity, true);

            // when
            final Bauprogramm updated = repository.update(updateEntity);

            // then
            assertThat(updated).isNotNull();
            assertThat(updated).isEqualTo(updateEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowException() {
            // given
            final BigDecimal id = BigDecimal.valueOf(10);
            final Bauprogramm updateEntity = new Bauprogramm(id, "Test (updated)");

            // when
            final Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> repository.update(updateEntity));

            // then
            assertThat(exception.getMessage())
                    .isEqualTo(String.format("%s does not exist: %s", updateEntity.getClass().getCanonicalName(), id.intValueExact()));
        }

    }

}
