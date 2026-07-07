package de.muenchen.oss.foerdermittel.backend.stadtbezirk;

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
class StadtbezirkServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private StadtbezirkRepository stadtbezirkRepository;

    @InjectMocks
    private StadtbezirkService unitUnderTest;

    @Nested
    class GetStadtbezirk {
        @Test
        void givenIdExists_thenReturnEntity() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Stadtbezirk entity = new Stadtbezirk(id, BEZEICHNUNG);
            when(stadtbezirkRepository.findById(id)).thenReturn(Optional.of(entity));

            // When
            final Stadtbezirk result = unitUnderTest.getStadtbezirk(id);

            // Then
            verify(stadtbezirkRepository, times(1)).findById(id);
            assertThat(result).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(stadtbezirkRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.getStadtbezirk(id));

            // Then
            verify(stadtbezirkRepository, times(1)).findById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetAllStadtbezirke {
        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Stadtbezirk entity1 = new Stadtbezirk(BigDecimal.valueOf(1), "Test 1");
            final Stadtbezirk entity2 = new Stadtbezirk(BigDecimal.valueOf(2), "Test 2");
            final List<Stadtbezirk> entities = Arrays.asList(entity1, entity2);
            final Page<Stadtbezirk> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(stadtbezirkRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Stadtbezirk> result = unitUnderTest.getAllStadtbezirke(pageable);

            // Then
            verify(stadtbezirkRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateStadtbezirk {
        @Test
        void givenStadtbezirk_thenCallInsertEntity() {
            // Given
            final Stadtbezirk entityToInsert = new Stadtbezirk(BigDecimal.valueOf(1), BEZEICHNUNG);
            final Stadtbezirk expectedEntity = new Stadtbezirk(BigDecimal.valueOf(1), BEZEICHNUNG);
            when(stadtbezirkRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Stadtbezirk result = unitUnderTest.createStadtbezirk(entityToInsert);

            // Then
            verify(stadtbezirkRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

    }

    @Nested
    class UpdateStadtbezirk {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Stadtbezirk entityToUpdate = new Stadtbezirk(id, "updated");
            final Stadtbezirk foundEntity = new Stadtbezirk(id, BEZEICHNUNG);
            final Stadtbezirk expectedEntity = new Stadtbezirk(id, "updated");
            when(stadtbezirkRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(stadtbezirkRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Stadtbezirk result = unitUnderTest.updateStadtbezirk(entityToUpdate, id);

            // Then
            verify(stadtbezirkRepository, times(1)).findById(id);
            verify(stadtbezirkRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Stadtbezirk entityToUpdate = new Stadtbezirk(id, BEZEICHNUNG);
            when(stadtbezirkRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateStadtbezirk(entityToUpdate, id));

            // Then
            verify(stadtbezirkRepository, times(1)).findById(id);
            verify(stadtbezirkRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class DeleteStadtbezirk {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(stadtbezirkRepository.findById(id)).thenReturn(Optional.of(new Stadtbezirk()));
            Mockito.doNothing().when(stadtbezirkRepository).deleteById(id);

            // When
            unitUnderTest.deleteStadtbezirk(id);

            // Then
            verify(stadtbezirkRepository, times(1)).findById(id);
            verify(stadtbezirkRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(stadtbezirkRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteStadtbezirk(id));

            // Then
            verify(stadtbezirkRepository, times(1)).findById(id);
            verify(stadtbezirkRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetStadtbezirkFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<BigDecimal> allStadtbezirke = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
            when(stadtbezirkRepository.findAllStadtbezirke()).thenReturn(allStadtbezirke);

            // When
            final StadtbezirkFormContext formContext = unitUnderTest.getStadtbezirkFormContext();

            // Then
            verify(stadtbezirkRepository, times(1)).findAllStadtbezirke();
            assertThat(formContext.stadtbezirke()).isEqualTo(allStadtbezirke);
        }
    }
}
