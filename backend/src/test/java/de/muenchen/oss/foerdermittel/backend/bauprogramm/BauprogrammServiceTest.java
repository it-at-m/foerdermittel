package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import static org.assertj.core.api.Assertions.assertThat;
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
class BauprogrammServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private BauprogrammRepository bauprogrammRepository;

    @InjectMocks
    private BauprogrammService unitUnderTest;

    @Nested
    class GetBauprogramm {
        @Test
        void givenIdExists_thenReturnEntity() {
            // Given
            final int id = 1;
            final Bauprogramm entity = new Bauprogramm(BigDecimal.valueOf(id), BEZEICHNUNG);
            when(bauprogrammRepository.findById(id)).thenReturn(Optional.of(entity));

            // When
            final Bauprogramm result = unitUnderTest.getBauprogramm(id);

            // Then
            verify(bauprogrammRepository).findById(id);
            assertThat(result).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final int id = 1;
            when(bauprogrammRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.getBauprogramm(id));

            // Then
            verify(bauprogrammRepository, times(1)).findById(id);
            assertThat(exception.getClass()).isEqualTo(NotFoundException.class);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with id %s\"", id));
        }
    }

    @Nested
    class GetAllBauprogramme {
        @Test
        void givenPageNumberAndPageSize_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageRequest = PageRequest.of(pageNumber, pageSize);

            final Bauprogramm entity1 = new Bauprogramm(BigDecimal.valueOf(1), "Test 1");
            final Bauprogramm entity2 = new Bauprogramm(BigDecimal.valueOf(2), "Test 2");
            final List<Bauprogramm> entities = Arrays.asList(entity1, entity2);
            final Page<Bauprogramm> expectedPage = new PageImpl<>(entities, pageRequest, entities.size());

            when(bauprogrammRepository.findAll(pageRequest)).thenReturn(expectedPage);

            // When
            final Page<Bauprogramm> result = unitUnderTest.getAllBauprogramme(pageNumber, pageSize);

            // Then
            verify(bauprogrammRepository, times(1)).findAll(pageRequest);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateBauprogramm {
        @Test
        void givenBauprogramm_thenReturnEntity() {
            // Given
            final Bauprogramm entityToSave = new Bauprogramm(BigDecimal.valueOf(1), BEZEICHNUNG);
            final Bauprogramm expectedEntity = new Bauprogramm(BigDecimal.valueOf(1), BEZEICHNUNG);
            when(bauprogrammRepository.save(entityToSave)).thenReturn(expectedEntity);

            // When
            final Bauprogramm result = unitUnderTest.createBauprogramm(entityToSave);

            // Then
            verify(bauprogrammRepository, times(1)).save(entityToSave);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }
    }

    @Nested
    class UpdateBauprogramm {
        @Test
        void givenEntityExists_thenReturnEntity() {
            // Given
            final int id = 1;
            final Bauprogramm entityToUpdate = new Bauprogramm(BigDecimal.valueOf(id), BEZEICHNUNG);
            final Bauprogramm expectedEntity = new Bauprogramm(BigDecimal.valueOf(id), BEZEICHNUNG);
            when(bauprogrammRepository.save(entityToUpdate)).thenReturn(expectedEntity);
            when(bauprogrammRepository.findById(id)).thenReturn(Optional.of(entityToUpdate));

            // When
            final Bauprogramm result = unitUnderTest.updateBauprogramm(entityToUpdate, id);

            // Then
            verify(bauprogrammRepository, times(1)).findById(id);
            verify(bauprogrammRepository, times(1)).save(entityToUpdate);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final int id = 1;
            final Bauprogramm entityToUpdate = new Bauprogramm(BigDecimal.valueOf(id), BEZEICHNUNG);
            when(bauprogrammRepository.findById(id)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateBauprogramm(entityToUpdate, id));

            // Then
            verify(bauprogrammRepository, times(1)).findById(id);
            verify(bauprogrammRepository, times(0)).save(entityToUpdate);
            assertThat(exception.getClass()).isEqualTo(NotFoundException.class);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with id %s\"", id));
        }
    }

    @Nested
    class DeleteBauprogramm {
        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final int id = 1;
            Mockito.doNothing().when(bauprogrammRepository).deleteById(id);

            // When
            unitUnderTest.deleteBauprogramm(id);

            // Then
            verify(bauprogrammRepository).deleteById(id);
        }
    }
}
