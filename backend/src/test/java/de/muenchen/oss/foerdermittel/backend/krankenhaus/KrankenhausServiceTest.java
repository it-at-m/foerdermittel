package de.muenchen.oss.foerdermittel.backend.krankenhaus;

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
class KrankenhausServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private KrankenhausRepository krankenhausRepository;

    @InjectMocks
    private KrankenhausService unitUnderTest;

    @Nested
    class GetKrankenhaus {
        @Test
        void givenIdExists_thenReturnEntity() {
            // Given
            final String id = "K";
            final Krankenhaus entity = new Krankenhaus(id, BEZEICHNUNG);
            when(krankenhausRepository.findById(id)).thenReturn(Optional.of(entity));

            // When
            final Krankenhaus result = unitUnderTest.getKrankenhaus(id);

            // Then
            verify(krankenhausRepository, times(1)).findById(id);
            assertThat(result).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            when(krankenhausRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.getKrankenhaus(id));

            // Then
            verify(krankenhausRepository, times(1)).findById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetAllKrankenhaeuser {
        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Krankenhaus entity1 = new Krankenhaus("K", "Test 1");
            final Krankenhaus entity2 = new Krankenhaus("L", "Test 2");
            final List<Krankenhaus> entities = Arrays.asList(entity1, entity2);
            final Page<Krankenhaus> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(krankenhausRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Krankenhaus> result = unitUnderTest.getAllKrankenhause(pageable);

            // Then
            verify(krankenhausRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateKrankenhaus {
        @Test
        void givenKrankenhaus_thenCallInsertEntity() {
            // Given
            final Krankenhaus entityToInsert = new Krankenhaus("K", BEZEICHNUNG);
            final Krankenhaus expectedEntity = new Krankenhaus("K", BEZEICHNUNG);
            when(krankenhausRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Krankenhaus result = unitUnderTest.createKrankenhaus(entityToInsert);

            // Then
            verify(krankenhausRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdateKrankenhaus {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final String id = "K";
            final Krankenhaus entityToUpdate = new Krankenhaus(id, "updated");
            final Krankenhaus foundEntity = new Krankenhaus(id, BEZEICHNUNG);
            final Krankenhaus expectedEntity = new Krankenhaus(id, "updated");
            when(krankenhausRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(krankenhausRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Krankenhaus result = unitUnderTest.updateKrankenhaus(entityToUpdate, id);

            // Then
            verify(krankenhausRepository, times(1)).findById(id);
            verify(krankenhausRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            final Krankenhaus entityToUpdate = new Krankenhaus(id, BEZEICHNUNG);
            when(krankenhausRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateKrankenhaus(entityToUpdate, id));

            // Then
            verify(krankenhausRepository, times(1)).findById(id);
            verify(krankenhausRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class DeleteKrankenhaus {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final String id = "K";
            when(krankenhausRepository.existsById(id)).thenReturn(true);
            Mockito.doNothing().when(krankenhausRepository).deleteById(id);

            // When
            unitUnderTest.deleteKrankenhaus(id);

            // Then
            verify(krankenhausRepository, times(1)).existsById(id);
            verify(krankenhausRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            when(krankenhausRepository.existsById(id)).thenReturn(false);

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteKrankenhaus(id));

            // Then
            verify(krankenhausRepository, times(1)).existsById(id);
            verify(krankenhausRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetKrankenhausFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<String> allKrhNamen = List.of("L", "K", "M");
            when(krankenhausRepository.findAllKrhNamen()).thenReturn(allKrhNamen);

            // When
            final KrankenhausFormContext formContext = unitUnderTest.getKrankenhausFormContext();

            // Then
            verify(krankenhausRepository, times(1)).findAllKrhNamen();
            assertThat(formContext.krhNamen()).isEqualTo(allKrhNamen);
        }

    }
}
