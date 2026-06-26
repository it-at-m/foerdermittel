package de.muenchen.oss.foerdermittel.backend.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

@ExtendWith(MockitoExtension.class)
public class InsertAndUpdateRepositoryÎmplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private JpaEntityInformation<Bauprogramm, BigDecimal> entityInformation;

    @InjectMocks
    private InsertAndUpdateRepositoryImpl<Bauprogramm, BigDecimal> repository;

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
    class Update {

        @Test
        void givenUpdateWithNoFlush_thenMergeWithoutFlush() {
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
        void givenUpdateWithFlush_thenMergeWithFlush() {
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
        void givenUpdateWhenNonExistent_thenThrowException() {
            // given
            Bauprogramm entity = new Bauprogramm();
            final BigDecimal id = BigDecimal.valueOf(42);

            when(entityInformation.getId(entity)).thenReturn(id);
            when(entityInformation.getJavaType()).thenReturn(Bauprogramm.class);
            when(entityManager.find(Bauprogramm.class, id)).thenReturn(null);

            final Exception exception = Assertions.assertThrows(EntityNotFoundException.class, () -> repository.update(entity));

            // then
            verify(entityManager, never()).merge(entity);
            assertThat(exception.getMessage()).isEqualTo(String.format("%s does not exist: %s", entity.getClass().getCanonicalName(), id));
        }

    }

}
