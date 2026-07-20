package de.muenchen.oss.foerdermittel.backend.kurzbezeichnungen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungFormContext;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungRepository;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
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
public class KurzbezeichnungServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private KurzbezeichnungRepository kurzbezeichnungRepository;

    @InjectMocks
    private KurzbezeichnungService unitUnderTest;

    @Nested
    class GetKurzbezeichnungen {
        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Kurzbezeichnung entity1 = new Kurzbezeichnung("K", "Test 1");
            final Kurzbezeichnung entity2 = new Kurzbezeichnung("L", "Test 2");
            final List<Kurzbezeichnung> entities = Arrays.asList(entity1, entity2);
            final Page<Kurzbezeichnung> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(kurzbezeichnungRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Kurzbezeichnung> result = unitUnderTest.getKurzbezeichnungen(pageable);

            // Then
            verify(kurzbezeichnungRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateKurzbezeichnung {
        @Test
        void givenKurzbezeichnung_thenCallInsertEntity() {
            // Given
            final Kurzbezeichnung entityToInsert = new Kurzbezeichnung("K", BEZEICHNUNG);
            final Kurzbezeichnung expectedEntity = new Kurzbezeichnung("K", BEZEICHNUNG);
            when(kurzbezeichnungRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Kurzbezeichnung result = unitUnderTest.createKurzbezeichnung(entityToInsert);

            // Then
            verify(kurzbezeichnungRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdateKurzbezeichnung {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final String id = "K";
            final Kurzbezeichnung entityToUpdate = new Kurzbezeichnung(id, "updated");
            final Kurzbezeichnung foundEntity = new Kurzbezeichnung(id, BEZEICHNUNG);
            final Kurzbezeichnung expectedEntity = new Kurzbezeichnung(id, "updated");
            when(kurzbezeichnungRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(kurzbezeichnungRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Kurzbezeichnung result = unitUnderTest.updateKurzbezeichnung(entityToUpdate, id);

            // Then
            verify(kurzbezeichnungRepository, times(1)).findById(id);
            verify(kurzbezeichnungRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            final Kurzbezeichnung entityToUpdate = new Kurzbezeichnung(id, BEZEICHNUNG);
            when(kurzbezeichnungRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateKurzbezeichnung(entityToUpdate, id));

            // Then
            verify(kurzbezeichnungRepository, times(1)).findById(id);
            verify(kurzbezeichnungRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class DeleteKurzbezeichnung {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final String id = "K";
            when(kurzbezeichnungRepository.findById(id)).thenReturn(Optional.of(new Kurzbezeichnung()));
            Mockito.doNothing().when(kurzbezeichnungRepository).deleteById(id);

            // When
            unitUnderTest.deleteKurzbezeichnung(id);

            // Then
            verify(kurzbezeichnungRepository, times(1)).findById(id);
            verify(kurzbezeichnungRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            when(kurzbezeichnungRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteKurzbezeichnung(id));

            // Then
            verify(kurzbezeichnungRepository, times(1)).findById(id);
            verify(kurzbezeichnungRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetKurzbezeichnungFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<String> allKurzBez = List.of("L", "K", "M");
            when(kurzbezeichnungRepository.findAllKurzbezeichnungen()).thenReturn(allKurzBez);

            // When
            final KurzbezeichnungFormContext formContext = unitUnderTest.getKurzbezeichnungFormContext();

            // Then
            verify(kurzbezeichnungRepository, times(1)).findAllKurzbezeichnungen();
            assertThat(formContext.kurzbezeichnungen()).isEqualTo(allKurzBez);
        }

    }
}
