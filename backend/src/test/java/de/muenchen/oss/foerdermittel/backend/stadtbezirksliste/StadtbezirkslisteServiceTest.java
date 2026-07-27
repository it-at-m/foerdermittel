package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkRepository;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Stadtbezirksliste;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteRepository;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameRepository;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StadtbezirkslisteServiceTest {
    private static final String BEZEICHNUNG = "testBezeichnung";

    @Mock
    private StadtbezirkslisteRepository stadtbezirkslisteRepository;
    @Mock
    private ListennameRepository listennameRepository;

    @InjectMocks
    private StadtbezirkslisteService unitUnderTest;

    @Nested
    class GetListenname {

        @Test
        void givenIdExists_thenReturnEntity() {
            // Given
            final String kurzBez = "SL2";
            final Listenname entity = new Listenname(kurzBez, BEZEICHNUNG, new ArrayList<>());

            when(listennameRepository.findById(kurzBez))
                    .thenReturn(Optional.of(entity));

            // When
            final Listenname result = unitUnderTest.getListenname(kurzBez);

            // Then
            verify(listennameRepository, times(1)).findById(kurzBez);
            assertThat(result).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final String kurzBez = "555";
            when(listennameRepository.findById(kurzBez)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.getListenname(kurzBez));

            // Then
            verify(listennameRepository, times(1)).findById(kurzBez);
            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", kurzBez));
        }
   }
//
//    @Nested
//    class GetAllStadtbezirkslistee {
//        @Test
//        void givenPageable_thenReturnPageOfEntities() {
//            // Given
//            final int pageNumber = 0;
//            final int pageSize = 10;
//            final Pageable pageable = PageRequest.of(pageNumber, pageSize);
//
//            final Stadtbezirksliste entity1 = new Stadtbezirksliste(BigDecimal.valueOf(1), "Test 1");
//            final Stadtbezirksliste entity2 = new Stadtbezirksliste(BigDecimal.valueOf(2), "Test 2");
//            final List<Stadtbezirksliste> entities = Arrays.asList(entity1, entity2);
//            final Page<Stadtbezirksliste> expectedPage = new PageImpl<>(entities, pageable, entities.size());
//
//            when(stadtbezirkslisteRepository.findAll(pageable)).thenReturn(expectedPage);
//
//            // When
//            final Page<Stadtbezirksliste> result = unitUnderTest.getAllStadtbezirkslistee(pageable);
//
//            // Then
//            verify(stadtbezirkslisteRepository, times(1)).findAll(pageable);
//            assertThat(result).isEqualTo(expectedPage);
//        }
//    }
//
//    @Nested
//    class CreateStadtbezirksliste {
//        @Test
//        void givenStadtbezirksliste_thenCallInsertEntity() {
//            // Given
//            final Stadtbezirksliste entityToInsert = new Stadtbezirksliste(BigDecimal.valueOf(1), BEZEICHNUNG);
//            final Stadtbezirksliste expectedEntity = new Stadtbezirksliste(BigDecimal.valueOf(1), BEZEICHNUNG);
//            when(stadtbezirkslisteRepository.insert(entityToInsert)).thenReturn(expectedEntity);
//
//            // When
//            final Stadtbezirksliste result = unitUnderTest.createStadtbezirksliste(entityToInsert);
//
//            // Then
//            verify(stadtbezirkslisteRepository, times(1)).insert(entityToInsert);
//            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
//        }
//
//    }
//
//    @Nested
//    class UpdateStadtbezirksliste {
//        @Test
//        void givenEntityExists_thenReturnEntity() {
//            // Given
//            final BigDecimal id = BigDecimal.valueOf(1);
//            final Stadtbezirksliste entityToUpdate = new Stadtbezirksliste(id, "updated");
//            final Stadtbezirksliste foundEntity = new Stadtbezirksliste(id, BEZEICHNUNG);
//            final Stadtbezirksliste expectedEntity = new Stadtbezirksliste(id, "updated");
//            when(stadtbezirkslisteRepository.findById(id)).thenReturn(Optional.of(foundEntity));
//            when(stadtbezirkslisteRepository.update(foundEntity)).thenReturn(expectedEntity);
//
//            // When
//            final Stadtbezirksliste result = unitUnderTest.updateStadtbezirksliste(entityToUpdate, id);
//
//            // Then
//            verify(stadtbezirkslisteRepository, times(1)).findById(id);
//            verify(stadtbezirkslisteRepository, times(1)).update(foundEntity);
//            assertThat(result).usingRecursiveComparison().isEqualTo(expectedEntity);
//        }
//
//        @Test
//        void givenEntityNotExists_thenThrowNotFoundException() {
//            // Given
//            final BigDecimal id = BigDecimal.valueOf(1);
//            final Stadtbezirksliste entityToUpdate = new Stadtbezirksliste(id, BEZEICHNUNG);
//            when(stadtbezirkslisteRepository.findById(id)).thenReturn(Optional.empty());
//
//            // When
//            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.updateStadtbezirksliste(entityToUpdate, id));
//
//            // Then
//            verify(stadtbezirkslisteRepository, times(1)).findById(id);
//            verify(stadtbezirkslisteRepository, never()).update(entityToUpdate);
//            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
//        }
//    }
//
//    @Nested
//    class DeleteStadtbezirksliste {
//        @Test
//        void givenIdExists_thenReturnVoid() {
//            // Given
//            final BigDecimal id = BigDecimal.valueOf(1);
//            when(stadtbezirkslisteRepository.findById(id)).thenReturn(Optional.of(new Stadtbezirksliste()));
//            Mockito.doNothing().when(stadtbezirkslisteRepository).deleteById(id);
//
//            // When
//            unitUnderTest.deleteStadtbezirksliste(id);
//
//            // Then
//            verify(stadtbezirkslisteRepository, times(1)).findById(id);
//            verify(stadtbezirkslisteRepository, times(1)).deleteById(id);
//        }
//
//        @Test
//        void givenIdNotExists_thenThrowNotFoundException() {
//            // Given
//            final BigDecimal id = BigDecimal.valueOf(1);
//            when(stadtbezirkslisteRepository.findById(id)).thenReturn(Optional.empty());
//
//            // When
//            final Exception exception = Assertions.assertThrows(NotFoundException.class, () -> unitUnderTest.deleteStadtbezirksliste(id));
//
//            // Then
//            verify(stadtbezirkslisteRepository, times(1)).findById(id);
//            verify(stadtbezirkslisteRepository, never()).deleteById(id);
//            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", id));
//        }
//    }
//
//    @Nested
//    class GetStadtbezirkslisteFormContext {
//
//        @Test
//        void givenEntitiesExists_thenReturnCorrectFormContext() {
//            // Given
//            final List<BigDecimal> allStadtbezirkslistee = List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4));
//            when(stadtbezirkslisteRepository.findAllStadtbezirkslistee()).thenReturn(allStadtbezirkslistee);
//
//            // When
//            final StadtbezirkslisteFormContext formContext = unitUnderTest.getStadtbezirkslisteFormContext();
//
//            // Then
//            verify(stadtbezirkslisteRepository, times(1)).findAllStadtbezirkslistee();
//            assertThat(formContext.stadtbezirkslistee()).isEqualTo(allStadtbezirkslistee);
//        }
//    }
}
