package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BauprogrammServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private BauprogrammRepository bauprogrammRepository;

    @InjectMocks
    private BauprogrammService unitUnderTest;

    @Nested
    class GetBauprogramm {
        @Test
        void givenIdExists_thenReturnEntity() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Bauprogramm entity = new Bauprogramm(id, BEZEICHNUNG);
            when(bauprogrammRepository.findById(id)).thenReturn(Optional.of(entity));

            // When
            final Bauprogramm result = unitUnderTest.getBauprogramm(id);

            // Then
            verify(bauprogrammRepository, times(1)).findById(id);
            assertThat(result).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(bauprogrammRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.getBauprogramm(id));

            // Then
            verify(bauprogrammRepository, times(1)).findById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetAllBauprogramme {
        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Bauprogramm entity1 = new Bauprogramm(BigDecimal.valueOf(1), "Test 1");
            final Bauprogramm entity2 = new Bauprogramm(BigDecimal.valueOf(2), "Test 2");
            final List<Bauprogramm> entities = Arrays.asList(entity1, entity2);
            final Page<Bauprogramm> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(bauprogrammRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Bauprogramm> result = unitUnderTest.getAllBauprogramme(pageable);

            // Then
            verify(bauprogrammRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateBauprogramm {
        @Test
        void givenBauprogramm_thenCallInsertEntity() {
            // Given
            final Bauprogramm entityToInsert = new Bauprogramm(BigDecimal.valueOf(1), BEZEICHNUNG);
            final Bauprogramm expectedEntity = new Bauprogramm(BigDecimal.valueOf(1), BEZEICHNUNG);
            when(bauprogrammRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Bauprogramm result = unitUnderTest.createBauprogramm(entityToInsert);

            // Then
            verify(bauprogrammRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdateBauprogramm {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Bauprogramm entityToUpdate = new Bauprogramm(id, BEZEICHNUNG);
            final Bauprogramm expectedEntity = new Bauprogramm(id, BEZEICHNUNG);
            when(bauprogrammRepository.update(entityToUpdate)).thenReturn(expectedEntity);
            when(bauprogrammRepository.findById(id)).thenReturn(Optional.of(entityToUpdate));

            // When
            final Bauprogramm result = unitUnderTest.updateBauprogramm(entityToUpdate, id);

            // Then
            verify(bauprogrammRepository, times(1)).findById(id);
            verify(bauprogrammRepository, times(1)).update(entityToUpdate);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Bauprogramm entityToUpdate = new Bauprogramm(id, BEZEICHNUNG);
            when(bauprogrammRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateBauprogramm(entityToUpdate, id));

            // Then
            verify(bauprogrammRepository, times(1)).findById(id);
            verify(bauprogrammRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class DeleteBauprogramm {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(bauprogrammRepository.existsById(id)).thenReturn(true);
            Mockito.doNothing().when(bauprogrammRepository).deleteById(id);

            // When
            unitUnderTest.deleteBauprogramm(id);

            // Then
            verify(bauprogrammRepository, times(1)).existsById(id);
            verify(bauprogrammRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(bauprogrammRepository.existsById(id)).thenReturn(false);

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteBauprogramm(id));

            // Then
            verify(bauprogrammRepository, times(1)).existsById(id);
            verify(bauprogrammRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetBauprogrammFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<BigDecimal> allBauprogramme = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
            when(bauprogrammRepository.findAllBauprogramme()).thenReturn(allBauprogramme);

            // When
            final BauprogrammFormContext formContext = unitUnderTest.getBauprogrammFormContext();

            // Then
            verify(bauprogrammRepository, times(1)).findAllBauprogramme();
            assertThat(formContext.bauprogramme()).isEqualTo(allBauprogramme);
        }

    }
}
