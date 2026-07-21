package de.muenchen.oss.foerdermittel.backend.hauptabschnitt;

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
class HauptabschnittServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private HauptabschnittRepository hauptabschnittRepository;

    @InjectMocks
    private HauptabschnittService unitUnderTest;

    @Nested
    class GetHauptabschnitte {
        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Hauptabschnitt entity1 = new Hauptabschnitt("K", "Test 1");
            final Hauptabschnitt entity2 = new Hauptabschnitt("L", "Test 2");
            final List<Hauptabschnitt> entities = Arrays.asList(entity1, entity2);
            final Page<Hauptabschnitt> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(hauptabschnittRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Hauptabschnitt> result = unitUnderTest.getHauptabschnitte(pageable);

            // Then
            verify(hauptabschnittRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateHauptabschnitt {
        @Test
        void givenHauptabschnitt_thenCallInsertEntity() {
            // Given
            final Hauptabschnitt entityToInsert = new Hauptabschnitt("K", BEZEICHNUNG);
            final Hauptabschnitt expectedEntity = new Hauptabschnitt("K", BEZEICHNUNG);
            when(hauptabschnittRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Hauptabschnitt result = unitUnderTest.createHauptabschnitt(entityToInsert);

            // Then
            verify(hauptabschnittRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdateHauptabschnitt {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final String id = "K";
            final Hauptabschnitt entityToUpdate = new Hauptabschnitt(id, "updated");
            final Hauptabschnitt foundEntity = new Hauptabschnitt(id, BEZEICHNUNG);
            final Hauptabschnitt expectedEntity = new Hauptabschnitt(id, "updated");
            when(hauptabschnittRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(hauptabschnittRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Hauptabschnitt result = unitUnderTest.updateHauptabschnitt(entityToUpdate, id);

            // Then
            verify(hauptabschnittRepository, times(1)).findById(id);
            verify(hauptabschnittRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            final Hauptabschnitt entityToUpdate = new Hauptabschnitt(id, BEZEICHNUNG);
            when(hauptabschnittRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateHauptabschnitt(entityToUpdate, id));

            // Then
            verify(hauptabschnittRepository, times(1)).findById(id);
            verify(hauptabschnittRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class DeleteHauptabschnitt {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final String id = "K";
            when(hauptabschnittRepository.findById(id)).thenReturn(Optional.of(new Hauptabschnitt()));
            Mockito.doNothing().when(hauptabschnittRepository).deleteById(id);

            // When
            unitUnderTest.deleteHauptabschnitt(id);

            // Then
            verify(hauptabschnittRepository, times(1)).findById(id);
            verify(hauptabschnittRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            when(hauptabschnittRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteHauptabschnitt(id));

            // Then
            verify(hauptabschnittRepository, times(1)).findById(id);
            verify(hauptabschnittRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetHauptabschnittFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<String> allHas = List.of("L", "K", "M");
            when(hauptabschnittRepository.findAllHas()).thenReturn(allHas);

            // When
            final HauptabschnittFormContext formContext = unitUnderTest.getHauptabschnittFormContext();

            // Then
            verify(hauptabschnittRepository, times(1)).findAllHas();
            assertThat(formContext.has()).isEqualTo(allHas);
        }

    }
}
