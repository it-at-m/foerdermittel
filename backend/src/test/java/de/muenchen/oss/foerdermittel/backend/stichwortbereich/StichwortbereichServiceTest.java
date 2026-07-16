package de.muenchen.oss.foerdermittel.backend.stichwortbereich;

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
public class StichwortbereichServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private StichwortbereichRepository stichwortbereichRepository;

    @InjectMocks
    private StichwortbereichService unitUnderTest;

    @Nested
    class GetStichwortbereiche {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Stichwortbereich entity1 = new Stichwortbereich("K", "Test 1");
            final Stichwortbereich entity2 = new Stichwortbereich("L", "Test 2");
            final List<Stichwortbereich> entities = Arrays.asList(entity1, entity2);
            final Page<Stichwortbereich> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(stichwortbereichRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Stichwortbereich> result = unitUnderTest.getStichwortbereiche(pageable);

            // Then
            verify(stichwortbereichRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateStichwortbereich {
        @Test
        void givenStichwortbereich_thenCallInsertEntity() {
            // Given
            final Stichwortbereich entityToInsert = new Stichwortbereich("K", BEZEICHNUNG);
            final Stichwortbereich expectedEntity = new Stichwortbereich("K", BEZEICHNUNG);
            when(stichwortbereichRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Stichwortbereich result = unitUnderTest.createStichwortbereich(entityToInsert);

            // Then
            verify(stichwortbereichRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdateStichwortbereich {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final String id = "K";
            final Stichwortbereich entityToUpdate = new Stichwortbereich(id, "updated");
            final Stichwortbereich foundEntity = new Stichwortbereich(id, BEZEICHNUNG);
            final Stichwortbereich expectedEntity = new Stichwortbereich(id, "updated");
            when(stichwortbereichRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(stichwortbereichRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Stichwortbereich result = unitUnderTest.updateStichwortbereich(entityToUpdate, id);

            // Then
            verify(stichwortbereichRepository, times(1)).findById(id);
            verify(stichwortbereichRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            final Stichwortbereich entityToUpdate = new Stichwortbereich(id, BEZEICHNUNG);
            when(stichwortbereichRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateStichwortbereich(entityToUpdate, id));

            // Then
            verify(stichwortbereichRepository, times(1)).findById(id);
            verify(stichwortbereichRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class DeleteStichwortbereich {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final String id = "K";
            when(stichwortbereichRepository.findById(id)).thenReturn(Optional.of(new Stichwortbereich()));
            Mockito.doNothing().when(stichwortbereichRepository).deleteById(id);

            // When
            unitUnderTest.deleteStichwortbereich(id);

            // Then
            verify(stichwortbereichRepository, times(1)).findById(id);
            verify(stichwortbereichRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            when(stichwortbereichRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteStichwortbereich(id));

            // Then
            verify(stichwortbereichRepository, times(1)).findById(id);
            verify(stichwortbereichRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetStichwortbereichFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<String> allBereiche = List.of("L", "K", "M");
            when(stichwortbereichRepository.findAllStichwortbereiche()).thenReturn(allBereiche);

            // When
            final StichwortbereichFormContext formContext = unitUnderTest.getStichwortbereichFormContext();

            // Then
            verify(stichwortbereichRepository, times(1)).findAllStichwortbereiche();
            assertThat(formContext.bereiche()).isEqualTo(allBereiche);
        }

    }
}
