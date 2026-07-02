package de.muenchen.oss.foerdermittel.backend.bauleitung;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
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
class BauleitungServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private BauleitungRepository bauleitungRepository;

    @InjectMocks
    private BauleitungService unitUnderTest;

    @Nested
    class GetBauleitung {
        @Test
        void givenIdExists_thenReturnEntity() {
            // Given
            final String id = "1";
            final Bauleitung entity = new Bauleitung(id, BEZEICHNUNG);
            when(bauleitungRepository.findById(id)).thenReturn(Optional.of(entity));

            // When
            final Bauleitung result = unitUnderTest.getBauleitung(id);

            // Then
            verify(bauleitungRepository, times(1)).findById(id);
            assertThat(result).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "1";
            when(bauleitungRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.getBauleitung(id));

            // Then
            verify(bauleitungRepository, times(1)).findById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetAllBauleitungen {
        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Bauleitung entity1 = new Bauleitung("1", "Test 1");
            final Bauleitung entity2 = new Bauleitung("2", "Test 2");
            final List<Bauleitung> entities = Arrays.asList(entity1, entity2);
            final Page<Bauleitung> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(bauleitungRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Bauleitung> result = unitUnderTest.getAllBauleitungen(pageable);

            // Then
            verify(bauleitungRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateBauleitung {
        @Test
        void givenBauleitung_thenCallInsertEntity() {
            // Given
            final Bauleitung entityToInsert = new Bauleitung("1", BEZEICHNUNG);
            final Bauleitung expectedEntity = new Bauleitung("2", BEZEICHNUNG);
            when(bauleitungRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Bauleitung result = unitUnderTest.createBauleitung(entityToInsert);

            // Then
            verify(bauleitungRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdateBauleitung {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final String id = "1";
            final Bauleitung entityToUpdate = new Bauleitung(id, "updated");
            final Bauleitung foundEntity = new Bauleitung(id, BEZEICHNUNG);
            final Bauleitung expectedEntity = new Bauleitung(id, "updated");
            when(bauleitungRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(bauleitungRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Bauleitung result = unitUnderTest.updateBauleitung(entityToUpdate, id);

            // Then
            verify(bauleitungRepository, times(1)).findById(id);
            verify(bauleitungRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "1";
            final Bauleitung entityToUpdate = new Bauleitung(id, BEZEICHNUNG);
            when(bauleitungRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateBauleitung(entityToUpdate, id));

            // Then
            verify(bauleitungRepository, times(1)).findById(id);
            verify(bauleitungRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class DeleteBauleitung {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final String id = "1";
            when(bauleitungRepository.existsById(id)).thenReturn(true);
            Mockito.doNothing().when(bauleitungRepository).deleteById(id);

            // When
            unitUnderTest.deleteBauleitung(id);

            // Then
            verify(bauleitungRepository, times(1)).existsById(id);
            verify(bauleitungRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "1";
            when(bauleitungRepository.existsById(id)).thenReturn(false);

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteBauleitung(id));

            // Then
            verify(bauleitungRepository, times(1)).existsById(id);
            verify(bauleitungRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetBauleitungFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<String> allBauleitungen = List.of("2", "3", "4");
            when(bauleitungRepository.findAllBauleitungen()).thenReturn(allBauleitungen);

            // When
            final BauleitungFormContext formContext = unitUnderTest.getBauleitungFormContext();

            // Then
            verify(bauleitungRepository, times(1)).findAllBauleitungen();
            assertThat(formContext.bauleitungen()).isEqualTo(allBauleitungen);
        }

    }
}
