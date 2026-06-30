package de.muenchen.oss.foerdermittel.backend.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceUnitUtil;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.persistence.metamodel.Type;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
public class InsertAndUpdateRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Mock
    private PersistenceUnitUtil persistenceUnitUtil;

    @Mock
    private Metamodel metamodel;

    @Mock
    private EntityType<Bauprogramm> entityType;

    @Mock
    private Type<BigDecimal> idType;

    @InjectMocks
    private InsertAndUpdateRepositoryImpl<Bauprogramm> repository;

    @Nested
    class Insert {

        @Test
        void givenInsertWithNoFlush_thenPersistWithoutFlush() {
            // given
            final Bauprogramm entity = new Bauprogramm();

            // when
            repository.insert(entity);

            // then
            verify(entityManager, times(1)).persist(entity);
            verify(entityManager, never()).flush();
        }

        @Test
        void givenInsertWithFlush_thenPersistWithFlush() {
            // given
            final Bauprogramm entity = new Bauprogramm();

            // when
            repository.insert(entity, true);

            // then
            verify(entityManager, times(1)).persist(entity);
            verify(entityManager, times(1)).flush();
        }

    }

    @Nested
    @MockitoSettings(strictness = Strictness.LENIENT)
    class Update {

        @BeforeEach
        void setUp() {
            when(entityManager.getEntityManagerFactory()).thenReturn(entityManagerFactory);
            when(entityManagerFactory.getPersistenceUnitUtil()).thenReturn(persistenceUnitUtil);
            when(entityManager.getMetamodel()).thenReturn(metamodel);

            when(metamodel.entity(Bauprogramm.class)).thenReturn(entityType);
            when(entityType.getIdType()).thenReturn((Type) idType);
            when(idType.getJavaType()).thenReturn(BigDecimal.class);
        }

        @Test
        void givenUpdateWithNoFlush_thenMergeWithoutFlush() {
            // given
            final BigDecimal id = BigDecimal.valueOf(42);
            final Bauprogramm entity = new Bauprogramm(id, "Test");

            when(persistenceUnitUtil.getIdentifier(entity)).thenReturn(id);
            when(entityManager.find(Bauprogramm.class, id)).thenReturn(entity);
            when(entityManager.merge(entity)).thenReturn(entity);

            // when
            repository.update(entity);

            // then
            verify(entityManager, times(1)).merge(entity);
            verify(entityManager, never()).flush();
        }

        @Test
        void givenUpdateWithFlush_thenMergeWithFlush() {
            // given
            final BigDecimal id = BigDecimal.valueOf(42);
            final Bauprogramm entity = new Bauprogramm(id, "Test");

            when(persistenceUnitUtil.getIdentifier(entity)).thenReturn(id);
            when(entityManager.find(Bauprogramm.class, id)).thenReturn(entity);
            when(entityManager.merge(entity)).thenReturn(entity);

            // when
            repository.update(entity, true);

            // then
            verify(entityManager, times(1)).merge(entity);
            verify(entityManager, times(1)).flush();
        }

        @Test
        void givenUpdateNoIdGiven_thenThrowIllegalStateException() {
            // given
            Bauprogramm entity = new Bauprogramm();

            when(persistenceUnitUtil.getIdentifier(entity)).thenReturn(null);

            // when
            final Exception exception = assertThrows(IllegalStateException.class, () -> repository.update(entity));

            // then
            verify(entityManager, never()).merge(entity);
            assertThat(exception.getMessage()).isEqualTo("Entity ID must not be null");
        }

        @Test
        void givenUnexpectedIdType_thenThrowIllegalStateException() {
            // given
            String idInvalidType = "1";
            Bauprogramm entity = new Bauprogramm();

            when(persistenceUnitUtil.getIdentifier(entity)).thenReturn(idInvalidType);

            // when
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> repository.update(entity));

            // then
            assertThat(exception.getMessage()).isEqualTo(String.format("Unexpected ID type: %s", idInvalidType.getClass()));
            verify(entityManager, never()).merge(entity);
        }

        @Test
        void givenEntityDoesNotExists_thenThrowEntityNotFoundException() {
            // given
            final BigDecimal id = BigDecimal.valueOf(42);
            Bauprogramm entity = new Bauprogramm(id, "Test");

            when(persistenceUnitUtil.getIdentifier(entity)).thenReturn(id);
            when(entityManager.find(Bauprogramm.class, id)).thenReturn(null);

            // when
            EntityNotFoundException exception = assertThrows(
                    EntityNotFoundException.class,
                    () -> repository.update(entity));

            // then
            assertThat(exception.getMessage()).isEqualTo(String.format("%s does not exist: %s", entity.getClass().getCanonicalName(), id));
            verify(entityManager, never()).merge(entity);
        }

    }

}
