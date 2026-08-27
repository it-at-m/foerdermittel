package de.muenchen.oss.foerdermittel.backend.unterabschnitt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.HauptabschnittService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextHauptabschnitt;
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
class UnterabschnittServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private UnterabschnittRepository unterabschnittRepository;

    @Mock
    private HauptabschnittService hauptabschnittService;

    @InjectMocks
    private UnterabschnittService unitUnderTest;

    @Nested
    class GetUnterabschnitte {
        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Unterabschnitt entity1 = new Unterabschnitt("K", "Test 1", new Hauptabschnitt("H1", BEZEICHNUNG));
            final Unterabschnitt entity2 = new Unterabschnitt("L", "Test 2", new Hauptabschnitt("H2", BEZEICHNUNG));
            final List<Unterabschnitt> entities = Arrays.asList(entity1, entity2);
            final Page<Unterabschnitt> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(unterabschnittRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Unterabschnitt> result = unitUnderTest.getUnterabschnitte(pageable);

            // Then
            verify(unterabschnittRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateUnterabschnitt {
        @Test
        void givenUnterabschnitt_thenCallInsertEntity() {
            // Given
            final Hauptabschnitt testHa = new Hauptabschnitt("H3", BEZEICHNUNG);
            final String ha = testHa.getHa();
            final Unterabschnitt entityToInsert = new Unterabschnitt("K", BEZEICHNUNG, testHa);
            final Unterabschnitt expectedEntity = new Unterabschnitt("K", BEZEICHNUNG, testHa);

            when(hauptabschnittService.getHauptabschnitt(ha)).thenReturn(testHa);
            when(unterabschnittRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Unterabschnitt result = unitUnderTest.createUnterabschnitt(entityToInsert, ha);

            // Then
            verify(hauptabschnittService).getHauptabschnitt(ha);
            verify(unterabschnittRepository).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Test
    void givenHaNotExists_thenThrowNotFoundException() {
        final String ha = "H99";
        final Unterabschnitt entityToInsert = new Unterabschnitt("K", BEZEICHNUNG, null);

        when(hauptabschnittService.getHauptabschnitt(ha)).thenThrow(NotFoundException.class);

        Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.createUnterabschnitt(entityToInsert, ha));
    }

    @Nested
    class UpdateUnterabschnitt {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final String id = "K";
            final String ha = "H4";
            final Hauptabschnitt storedHa = new Hauptabschnitt(ha, BEZEICHNUNG);
            final Unterabschnitt entityToUpdate = new Unterabschnitt(id, "updated", null);
            final Unterabschnitt foundEntity = new Unterabschnitt(id, BEZEICHNUNG, storedHa);
            final Unterabschnitt expectedEntity = new Unterabschnitt(id, "updated", storedHa);

            when(unterabschnittRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(unterabschnittRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Unterabschnitt result = unitUnderTest.updateUnterabschnitt(entityToUpdate, id);

            // Then
            verify(unterabschnittRepository).findById(id);
            verify(unterabschnittRepository).update(foundEntity);

            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            final String ha = "H5";

            final Hauptabschnitt testHa = new Hauptabschnitt(ha, BEZEICHNUNG);
            final Unterabschnitt entityToUpdate = new Unterabschnitt(id, BEZEICHNUNG, testHa);

            when(unterabschnittRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateUnterabschnitt(entityToUpdate, id));

            // Then
            verify(unterabschnittRepository, times(1)).findById(id);
            verify(unterabschnittRepository, never()).update(Mockito.any(Unterabschnitt.class));
            verify(hauptabschnittService, never()).getHauptabschnitt(Mockito.anyString());

            assertThat(exception.getMessage()).isEqualTo(String.format("The %s with ID %s was not found.", Unterabschnitt.class.getSimpleName(), id));
        }
    }

    @Nested
    class DeleteUnterabschnitt {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final String id = "K";
            when(unterabschnittRepository.findById(id))
                    .thenReturn(Optional.of(new Unterabschnitt()));
            Mockito.doNothing().when(unterabschnittRepository).deleteById(id);

            // When
            unitUnderTest.deleteUnterabschnitt(id);

            // Then
            verify(unterabschnittRepository, times(1)).findById(id);
            verify(unterabschnittRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            when(unterabschnittRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.deleteUnterabschnitt(id));

            // Then
            verify(unterabschnittRepository, times(1)).findById(id);
            verify(unterabschnittRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("The %s with ID %s was not found.", Unterabschnitt.class.getSimpleName(), id));
        }
    }

    @Nested
    class GetUnterabschnittFormContext {

        @Test
        void givenEntitiesExist_thenReturnCorrectFormContext() {
            // Given
            final List<String> allUas = List.of("L", "K", "M");
            final Hauptabschnitt hauptabschnitt = new Hauptabschnitt("H1", BEZEICHNUNG);

            when(unterabschnittRepository.findAllUas()).thenReturn(allUas);
            when(hauptabschnittService.getAllHauptabschnitte())
                    .thenReturn(List.of(hauptabschnitt));

            // When
            final UnterabschnittFormContext formContext = unitUnderTest.getUnterabschnittFormContext();

            // Then
            verify(unterabschnittRepository).findAllUas();
            verify(hauptabschnittService).getAllHauptabschnitte();

            assertThat(formContext.uas()).isEqualTo(allUas);
            assertThat(formContext.hasHas()).containsExactly(new UnterabschnittFormContextHauptabschnitt("H1", BEZEICHNUNG));
        }
    }
}
