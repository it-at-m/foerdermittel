
package de.muenchen.oss.foerdermittel.backend.publikation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationFormContextDTO;
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
class PublikationServiceTest {

    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private PublikationRepository publikationRepository;

    @InjectMocks
    private PublikationService unitUnderTest;

    @Nested
    class GetPublikationen {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Publikation entity1 = new Publikation("K", "Test 1");
            final Publikation entity2 = new Publikation("L", "Test 2");
            final List<Publikation> entities = Arrays.asList(entity1, entity2);
            final Page<Publikation> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(publikationRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Publikation> result = unitUnderTest.getPublikationen(pageable);

            // Then
            verify(publikationRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreatePublikation {

        @Test
        void givenPublikation_thenCallInsertEntity() {
            // Given
            final Publikation entityToInsert = new Publikation("K", BEZEICHNUNG);
            final Publikation expectedEntity = new Publikation("K", BEZEICHNUNG);
            when(publikationRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Publikation result = unitUnderTest.createPublikation(entityToInsert);

            // Then
            verify(publikationRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdatePublikation {

        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final String id = "K";
            final Publikation entityToUpdate = new Publikation(id, "updated");
            final Publikation foundEntity = new Publikation(id, BEZEICHNUNG);
            final Publikation expectedEntity = new Publikation(id, "updated");

            when(publikationRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(publikationRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Publikation result = unitUnderTest.updatePublikation(entityToUpdate, id);

            // Then
            verify(publikationRepository, times(1)).findById(id);
            verify(publikationRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            final Publikation entityToUpdate = new Publikation(id, BEZEICHNUNG);
            when(publikationRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.updatePublikation(entityToUpdate, id));

            // Then
            verify(publikationRepository, times(1)).findById(id);
            verify(publikationRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(
                    String.format(
                            "The %s with ID %s was not found.",
                            Publikation.class.getSimpleName(),
                            id));
        }
    }

    @Nested
    class DeletePublikation {

        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final String id = "K";
            when(publikationRepository.findById(id)).thenReturn(Optional.of(new Publikation()));
            Mockito.doNothing().when(publikationRepository).deleteById(id);

            // When
            unitUnderTest.deletePublikation(id);

            // Then
            verify(publikationRepository, times(1)).findById(id);
            verify(publikationRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final String id = "K";
            when(publikationRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.deletePublikation(id));

            // Then
            verify(publikationRepository, times(1)).findById(id);
            verify(publikationRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(
                    String.format(
                            "The %s with ID %s was not found.",
                            Publikation.class.getSimpleName(),
                            id));
        }
    }

    @Nested
    class GetPublikationFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<String> allKurzformen = List.of("L", "K", "M");
            when(publikationRepository.findAllKurzformen()).thenReturn(allKurzformen);

            // When
            final PublikationFormContext formContext = unitUnderTest.getPublikationFormContext();

            // Then
            verify(publikationRepository, times(1)).findAllKurzformen();
            assertThat(formContext.kurzformen()).isEqualTo(allKurzformen);
        }
    }

    @Nested
    class GetPublikationFormContextDTO {

        private static final String KURZFORM = "K";

        @Test
        void givenValidValues_thenCreateDTO() {
            // Given
            final String kurzform = KURZFORM;
            final String bezeichnung = BEZEICHNUNG;

            // When
            final PublikationFormContextDTO result = new PublikationFormContextDTO(kurzform, bezeichnung);

            // Then
            assertThat(result.kurzform()).isEqualTo(kurzform);
            assertThat(result.bezeichnung()).isEqualTo(bezeichnung);
        }

        @Test
        void givenNullKurzform_thenCreateDTOWithNullKurzform() {
            // When
            final PublikationFormContextDTO result = new PublikationFormContextDTO(null, BEZEICHNUNG);

            // Then
            assertThat(result.kurzform()).isNull();
            assertThat(result.bezeichnung()).isEqualTo(BEZEICHNUNG);
        }

        @Test
        void givenNullBezeichnung_thenCreateDTOWithNullBezeichnung() {
            // When
            final PublikationFormContextDTO result = new PublikationFormContextDTO(KURZFORM, null);

            // Then
            assertThat(result.kurzform()).isEqualTo(KURZFORM);
            assertThat(result.bezeichnung()).isNull();
        }

        @Test
        void givenBothValuesNull_thenCreateDTOWithNullValues() {
            // When
            final PublikationFormContextDTO result = new PublikationFormContextDTO(null, null);

            // Then
            assertThat(result.kurzform()).isNull();
            assertThat(result.bezeichnung()).isNull();
        }

        @Test
        void givenEqualDTOs_thenTheyAreEqual() {
            // Given
            final PublikationFormContextDTO dto1 = new PublikationFormContextDTO(KURZFORM, BEZEICHNUNG);
            final PublikationFormContextDTO dto2 = new PublikationFormContextDTO(KURZFORM, BEZEICHNUNG);

            // Then
            assertThat(dto1)
                    .isEqualTo(dto2)
                    .hasSameHashCodeAs(dto2);
        }

        @Test
        void givenDifferentDTOs_thenTheyAreNotEqual() {
            // Given
            final PublikationFormContextDTO dto1 = new PublikationFormContextDTO(KURZFORM, BEZEICHNUNG);
            final PublikationFormContextDTO dto2 = new PublikationFormContextDTO("L", BEZEICHNUNG);

            // Then
            assertThat(dto1).isNotEqualTo(dto2);
        }
    }
}
