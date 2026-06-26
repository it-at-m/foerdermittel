package de.muenchen.oss.foerdermittel.backend.common;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InsertAndUpdateRepositoryÎmplTest {

    @Mock
    EntityManager entityManager;

    @Mock
    JpaEntityInformation<Bauprogramm, BigDecimal> entityInformation;

    @InjectMocks
    InsertAndUpdateRepositoryImpl<Bauprogramm, BigDecimal> repository;

    @Nested
    class Insert {

        @Test
        void givenInsertWithNoFlush_shouldPersistWithoutFlush() {
            // given
            final Bauprogramm entity = new Bauprogramm();

            // when
            repository.insert(entity);

            // then
            verify(entityManager, times(1)).persist(entity);
            verify(entityManager, never()).flush();
        }

        @Test
        void givenInsertWithFlush_shouldPersistWithFlush() {
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
    class Update {

        @Test
        void givenUpdateWithNoFlush_shouldMergeWithoutFlush() {
            // given
            final Bauprogramm entity = new Bauprogramm();
            final BigDecimal id = BigDecimal.valueOf(42);

            when(entityInformation.getId(entity)).thenReturn(id);
            when(entityInformation.getJavaType()).thenReturn(Bauprogramm.class);
            when(entityManager.find(Bauprogramm.class, id)).thenReturn(new Bauprogramm());
            when(entityManager.merge(entity)).thenReturn(entity);

            // when
            repository.update(entity);

            // then
            verify(entityManager, times(1)).merge(entity);
            verify(entityManager, never()).flush();
        }

        @Test
        void givenUpdateWithFlush_shouldMergeWithFlush() {
            // given
            Bauprogramm entity = new Bauprogramm();
            final BigDecimal id = BigDecimal.valueOf(42);

            when(entityInformation.getId(entity)).thenReturn(id);
            when(entityInformation.getJavaType()).thenReturn(Bauprogramm.class);
            when(entityManager.find(Bauprogramm.class, id)).thenReturn(entity);
            when(entityManager.merge(entity)).thenReturn(entity);

            // when
            repository.update(entity, true);

            // then
            verify(entityManager, times(1)).merge(entity);
            verify(entityManager, times(1)).flush();
        }

        @Test
        void givenUpdateWhenNonExistent_shouldThrowException() {
            // given
            Bauprogramm entity = new Bauprogramm();
            final BigDecimal id = BigDecimal.valueOf(42);

            when(entityInformation.getId(entity)).thenReturn(id);
            when(entityInformation.getJavaType()).thenReturn(Bauprogramm.class);
            when(entityManager.find(Bauprogramm.class, id)).thenReturn(null);

            final Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> repository.update(entity));

            // then
            verify(entityManager, never()).merge(entity);
            assertThat(exception.getMessage()).isEqualTo(String.format("Entity does not exist: %s", id));
        }

    }

}
