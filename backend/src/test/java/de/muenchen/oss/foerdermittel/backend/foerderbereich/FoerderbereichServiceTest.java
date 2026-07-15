package de.muenchen.oss.foerdermittel.backend.foerderbereich;

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
class FoerderbereichServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private FoerderbereichRepository foerderbereichRepository;

    @InjectMocks
    private FoerderbereichService unitUnderTest;

    @Nested
    class GetFoerderbereiche {
        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Foerderbereich entity1 = new Foerderbereich(BigDecimal.valueOf(1), "Test 1", false, true, false, true);
            final Foerderbereich entity2 = new Foerderbereich(BigDecimal.valueOf(2), "Test 2", false, true, false, true);
            final List<Foerderbereich> entities = Arrays.asList(entity1, entity2);
            final Page<Foerderbereich> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(foerderbereichRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Foerderbereich> result = unitUnderTest.getFoerderbereiche(pageable);

            // Then
            verify(foerderbereichRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateFoerderbereich {
        @Test
        void givenFoerderbereich_thenCallInsertEntity() {
            // Given
            final Foerderbereich entityToInsert = new Foerderbereich(BigDecimal.valueOf(1), BEZEICHNUNG, false, true, false, true);
            final Foerderbereich expectedEntity = new Foerderbereich(BigDecimal.valueOf(1), BEZEICHNUNG, false, true, false, true);
            when(foerderbereichRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Foerderbereich result = unitUnderTest.createFoerderbereich(entityToInsert);

            // Then
            verify(foerderbereichRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdateFoerderbereich {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Foerderbereich entityToUpdate = new Foerderbereich(id, "updated", false, true, false, true);
            final Foerderbereich foundEntity = new Foerderbereich(id, BEZEICHNUNG, false, true, false, true);
            final Foerderbereich expectedEntity = new Foerderbereich(id, "updated", false, true, false, true);
            when(foerderbereichRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(foerderbereichRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Foerderbereich result = unitUnderTest.updateFoerderbereich(entityToUpdate, id);

            // Then
            verify(foerderbereichRepository, times(1)).findById(id);
            verify(foerderbereichRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Foerderbereich entityToUpdate = new Foerderbereich(id, BEZEICHNUNG, false, true, false, true);
            when(foerderbereichRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateFoerderbereich(entityToUpdate, id));

            // Then
            verify(foerderbereichRepository, times(1)).findById(id);
            verify(foerderbereichRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class DeleteFoerderbereich {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(foerderbereichRepository.findById(id)).thenReturn(Optional.of(new Foerderbereich()));
            Mockito.doNothing().when(foerderbereichRepository).deleteById(id);

            // When
            unitUnderTest.deleteFoerderbereich(id);

            // Then
            verify(foerderbereichRepository, times(1)).findById(id);
            verify(foerderbereichRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(foerderbereichRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteFoerderbereich(id));

            // Then
            verify(foerderbereichRepository, times(1)).findById(id);
            verify(foerderbereichRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetFoerderbereichFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<BigDecimal> allFoerderbereiche = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
            when(foerderbereichRepository.findAllFb()).thenReturn(allFoerderbereiche);

            // When
            final FoerderbereichFormContext formContext = unitUnderTest.getFoerderbereichFormContext();

            // Then
            verify(foerderbereichRepository, times(1)).findAllFb();
            assertThat(formContext.fbs()).isEqualTo(allFoerderbereiche);
        }

    }
}
