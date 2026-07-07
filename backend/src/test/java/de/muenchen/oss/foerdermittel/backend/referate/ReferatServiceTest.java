package de.muenchen.oss.foerdermittel.backend.referate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.referat.Referat;
import de.muenchen.oss.foerdermittel.backend.referat.ReferatFormContext;
import de.muenchen.oss.foerdermittel.backend.referat.ReferatRepository;
import de.muenchen.oss.foerdermittel.backend.referat.ReferatService;
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
class ReferatServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private ReferatRepository referatRepository;

    @InjectMocks
    private ReferatService unitUnderTest;

    @Nested
    class GetReferat {
        @Test
        void givenIdExists_thenReturnEntity() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Referat entity = new Referat(id, BEZEICHNUNG);
            when(referatRepository.findById(id)).thenReturn(Optional.of(entity));

            // When
            final Referat result = unitUnderTest.getReferat(id);

            // Then
            verify(referatRepository, times(1)).findById(id);
            assertThat(result).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(referatRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.getReferat(id));

            // Then
            verify(referatRepository, times(1)).findById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetAllReferate {
        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Referat entity1 = new Referat(BigDecimal.valueOf(1), "Test 1");
            final Referat entity2 = new Referat(BigDecimal.valueOf(2), "Test 2");
            final List<Referat> entities = Arrays.asList(entity1, entity2);
            final Page<Referat> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(referatRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Referat> result = unitUnderTest.getAllReferate(pageable);

            // Then
            verify(referatRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateReferat {
        @Test
        void givenReferat_thenCallInsertEntity() {
            // Given
            final Referat entityToInsert = new Referat(BigDecimal.valueOf(1), BEZEICHNUNG);
            final Referat expectedEntity = new Referat(BigDecimal.valueOf(1), BEZEICHNUNG);
            when(referatRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Referat result = unitUnderTest.createReferat(entityToInsert);

            // Then
            verify(referatRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdateReferat {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Referat entityToUpdate = new Referat(id, "updated");
            final Referat foundEntity = new Referat(id, BEZEICHNUNG);
            final Referat expectedEntity = new Referat(id, "updated");
            when(referatRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(referatRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Referat result = unitUnderTest.updateReferat(entityToUpdate, id);

            // Then
            verify(referatRepository, times(1)).findById(id);
            verify(referatRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Referat entityToUpdate = new Referat(id, BEZEICHNUNG);
            when(referatRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateReferat(entityToUpdate, id));

            // Then
            verify(referatRepository, times(1)).findById(id);
            verify(referatRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class DeleteReferat {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(referatRepository.findById(id)).thenReturn(Optional.of(new Referat()));
            Mockito.doNothing().when(referatRepository).deleteById(id);

            // When
            unitUnderTest.deleteReferat(id);

            // Then
            verify(referatRepository, times(1)).findById(id);
            verify(referatRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(referatRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteReferat(id));

            // Then
            verify(referatRepository, times(1)).findById(id);
            verify(referatRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetReferatFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<BigDecimal> allReferate = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
            when(referatRepository.findAllRefNr()).thenReturn(allReferate);

            // When
            final ReferatFormContext formContext = unitUnderTest.getReferatFormContext();

            // Then
            verify(referatRepository, times(1)).findAllRefNr();
            assertThat(formContext.refNrs()).isEqualTo(allReferate);
        }

    }
}
