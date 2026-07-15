package de.muenchen.oss.foerdermittel.backend.traeger;

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
class TraegerServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private TraegerRepository traegerRepository;

    @InjectMocks
    private TraegerService unitUnderTest;

    @Nested
    class GetTraeger {
        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Traeger entity1 = new Traeger(BigDecimal.valueOf(1), "Test 1");
            final Traeger entity2 = new Traeger(BigDecimal.valueOf(2), "Test 2");
            final List<Traeger> entities = Arrays.asList(entity1, entity2);
            final Page<Traeger> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(traegerRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Traeger> result = unitUnderTest.getAllTraeger(pageable);

            // Then
            verify(traegerRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateTraeger {
        @Test
        void givenTraeger_thenCallInsertEntity() {
            // Given
            final Traeger entityToInsert = new Traeger(BigDecimal.valueOf(1), BEZEICHNUNG);
            final Traeger expectedEntity = new Traeger(BigDecimal.valueOf(1), BEZEICHNUNG);
            when(traegerRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Traeger result = unitUnderTest.createTraeger(entityToInsert);

            // Then
            verify(traegerRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdateTraeger {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Traeger entityToUpdate = new Traeger(id, "updated");
            final Traeger foundEntity = new Traeger(id, BEZEICHNUNG);
            final Traeger expectedEntity = new Traeger(id, "updated");
            when(traegerRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(traegerRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Traeger result = unitUnderTest.updateTraeger(entityToUpdate, id);

            // Then
            verify(traegerRepository, times(1)).findById(id);
            verify(traegerRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Traeger entityToUpdate = new Traeger(id, BEZEICHNUNG);
            when(traegerRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateTraeger(entityToUpdate, id));

            // Then
            verify(traegerRepository, times(1)).findById(id);
            verify(traegerRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class DeleteTraeger {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Traeger existingTraeger = new Traeger(id, "delete");
            when(traegerRepository.findById(id)).thenReturn(Optional.of(existingTraeger));
            Mockito.doNothing().when(traegerRepository).deleteById(id);

            // When
            unitUnderTest.deleteTraeger(id);

            // Then
            verify(traegerRepository, times(1)).findById(id);
            verify(traegerRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(traegerRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteTraeger(id));

            // Then
            verify(traegerRepository, times(1)).findById(id);
            verify(traegerRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetTraegerFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<BigDecimal> allTraeger = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
            when(traegerRepository.findAllKurzformen()).thenReturn(allTraeger);

            // When
            final TraegerFormContext formContext = unitUnderTest.getTraegerFormContext();

            // Then
            verify(traegerRepository, times(1)).findAllKurzformen();
            assertThat(formContext.kurzformen()).isEqualTo(allTraeger);
        }

    }
}
