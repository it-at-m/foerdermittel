package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet;

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
class SiedlungsgebietServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private SiedlungsgebietRepository siedlungsgebietRepository;

    @InjectMocks
    private SiedlungsgebietService unitUnderTest;

    @Nested
    class GetSiedlungsgebiet {
        @Test
        void givenIdExists_thenReturnEntity() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Siedlungsgebiet entity = new Siedlungsgebiet(id, BEZEICHNUNG);
            when(siedlungsgebietRepository.findById(id)).thenReturn(Optional.of(entity));

            // When
            final Siedlungsgebiet result = unitUnderTest.getSiedlungsgebiet(id);

            // Then
            verify(siedlungsgebietRepository, times(1)).findById(id);
            assertThat(result).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(siedlungsgebietRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.getSiedlungsgebiet(id));

            // Then
            verify(siedlungsgebietRepository, times(1)).findById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetAllSiedlungsgebiete {
        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Siedlungsgebiet entity1 = new Siedlungsgebiet(BigDecimal.valueOf(1), "Test 1");
            final Siedlungsgebiet entity2 = new Siedlungsgebiet(BigDecimal.valueOf(2), "Test 2");
            final List<Siedlungsgebiet> entities = Arrays.asList(entity1, entity2);
            final Page<Siedlungsgebiet> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(siedlungsgebietRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Siedlungsgebiet> result = unitUnderTest.getAllSiedlungsgebiete(pageable);

            // Then
            verify(siedlungsgebietRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateSiedlungsgebiet {
        @Test
        void givenSiedlungsgebiet_thenCallInsertEntity() {
            // Given
            final Siedlungsgebiet entityToInsert = new Siedlungsgebiet(BigDecimal.valueOf(1), BEZEICHNUNG);
            final Siedlungsgebiet expectedEntity = new Siedlungsgebiet(BigDecimal.valueOf(1), BEZEICHNUNG);
            when(siedlungsgebietRepository.insert(entityToInsert)).thenReturn(expectedEntity);

            // When
            final Siedlungsgebiet result = unitUnderTest.createSiedlungsgebiet(entityToInsert);

            // Then
            verify(siedlungsgebietRepository, times(1)).insert(entityToInsert);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

    }

    @Nested
    class UpdateSiedlungsgebiet {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Siedlungsgebiet entityToUpdate = new Siedlungsgebiet(id, "updated");
            final Siedlungsgebiet foundEntity = new Siedlungsgebiet(id, BEZEICHNUNG);
            final Siedlungsgebiet expectedEntity = new Siedlungsgebiet(id, "updated");
            when(siedlungsgebietRepository.findById(id)).thenReturn(Optional.of(foundEntity));
            when(siedlungsgebietRepository.update(foundEntity)).thenReturn(expectedEntity);

            // When
            final Siedlungsgebiet result = unitUnderTest.updateSiedlungsgebiet(entityToUpdate, id);

            // Then
            verify(siedlungsgebietRepository, times(1)).findById(id);
            verify(siedlungsgebietRepository, times(1)).update(foundEntity);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            final Siedlungsgebiet entityToUpdate = new Siedlungsgebiet(id, BEZEICHNUNG);
            when(siedlungsgebietRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateSiedlungsgebiet(entityToUpdate, id));

            // Then
            verify(siedlungsgebietRepository, times(1)).findById(id);
            verify(siedlungsgebietRepository, never()).update(entityToUpdate);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class DeleteSiedlungsgebiet {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(siedlungsgebietRepository.findById(id)).thenReturn(Optional.of(new Siedlungsgebiet()));
            Mockito.doNothing().when(siedlungsgebietRepository).deleteById(id);

            // When
            unitUnderTest.deleteSiedlungsgebiet(id);

            // Then
            verify(siedlungsgebietRepository, times(1)).findById(id);
            verify(siedlungsgebietRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final BigDecimal id = BigDecimal.valueOf(1);
            when(siedlungsgebietRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteSiedlungsgebiet(id));

            // Then
            verify(siedlungsgebietRepository, times(1)).findById(id);
            verify(siedlungsgebietRepository, never()).deleteById(id);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
        }
    }

    @Nested
    class GetSiedlungsgebietFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<BigDecimal> allSiedlungsgebiete = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
            when(siedlungsgebietRepository.findAllSiedlungsgebiete()).thenReturn(allSiedlungsgebiete);

            // When
            final SiedlungsgebietFormContext formContext = unitUnderTest.getSiedlungsgebietFormContext();

            // Then
            verify(siedlungsgebietRepository, times(1)).findAllSiedlungsgebiete();
            assertThat(formContext.siedlungsgebiete()).isEqualTo(allSiedlungsgebiete);
        }

    }
}
