package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class StadtbezirkslisteServiceTest {

    private static final String KURZBEZ = "ABC";
    private static final String BEZEICHNUNG = "Test Liste";

    @Mock
    private ListennameRepository listennameRepository;
    @Mock
    private StadtbezirkslisteRepository stadtbezirkslisteRepository;

    @InjectMocks
    private StadtbezirkslisteService unitUnderTest;

    @Nested
    class GetListenname {

        @Test
        void givenKurzbezExists_thenReturnEntity() {
            // Given
            final Listenname entity = new Listenname(KURZBEZ, BEZEICHNUNG, List.of());
            when(listennameRepository.findById(KURZBEZ)).thenReturn(Optional.of(entity));

            // When
            final Listenname result = unitUnderTest.getListenname(KURZBEZ);

            // Then
            verify(listennameRepository, times(1)).findById(KURZBEZ);
            assertThat(result).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void givenKurzbezNotExists_thenThrowNotFoundException() {
            // Given
            when(listennameRepository.findById(KURZBEZ)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.getListenname(KURZBEZ));

            // Then
            verify(listennameRepository, times(1)).findById(KURZBEZ);
            assertThat(exception.getMessage())
                    .isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", KURZBEZ));
        }
    }

    @Nested
    class GetAllListennamen {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final Pageable pageable = PageRequest.of(0, 10);
            final Listenname entity = new Listenname(KURZBEZ, BEZEICHNUNG, List.of());
            final Page<Listenname> expectedPage = new PageImpl<>(List.of(entity));

            when(listennameRepository.findAll(pageable)).thenReturn(expectedPage);

            // When
            final Page<Listenname> result = unitUnderTest.getAllListennamen(pageable);

            // Then
            verify(listennameRepository, times(1)).findAll(pageable);
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class GetStadtbezirksListeFormContext {

        @Test
        void givenKurzbezeichnungenExists_thenReturnFormContext() {
            // Given
            final List<String> kurzbezeichnungen = List.of("ABC", "DEF");
            when(listennameRepository.findAllKurzBezn()).thenReturn(kurzbezeichnungen);

            // When
            final StadtbezirkslisteFormContext result = unitUnderTest.getStadtbezirksListeFormContext();

            // Then
            verify(listennameRepository, times(1)).findAllKurzBezn();
            assertThat(result.stadtbezirksliste()).isEqualTo(kurzbezeichnungen);
        }
    }

    @Nested
    class CreateListenname {

        @Test
        void givenListenname_thenInsertEntity() {
            // Given
            final Stadtbezirk stadtbezirk = new Stadtbezirk(BigDecimal.ONE, "Bezirk");
            final Stadtbezirksliste assignment = new Stadtbezirksliste(null, null, stadtbezirk, "Test");

            final Listenname entity = new Listenname(KURZBEZ, BEZEICHNUNG, new ArrayList<>(List.of(assignment)));

            when(listennameRepository.insert(entity)).thenReturn(entity);

            // When
            final Listenname result = unitUnderTest.createListenname(entity);

            // Then
            verify(listennameRepository, times(1)).insert(entity);
            assertThat(assignment.getListenName()).isEqualTo(entity);
            assertThat(assignment.getId())
                    .isEqualTo(new StadtbezirkslistePrimaryKey(KURZBEZ, BigDecimal.ONE));
            assertThat(result).usingRecursiveComparison().isEqualTo(entity);
        }
    }

    @Nested
    class UpdateListenname {

        @Test
        void givenEntityExists_thenUpdateEntity() {
            // Given
            final Stadtbezirk stadtbezirk = new Stadtbezirk(BigDecimal.ONE, "Bezirk");

            final Stadtbezirksliste oldAssignment = new Stadtbezirksliste(null, null, stadtbezirk, "old");

            final Listenname foundEntity = new Listenname(KURZBEZ, "old", new java.util.ArrayList<>(List.of(oldAssignment)));

            final Stadtbezirksliste newAssignment = new Stadtbezirksliste(null, null, stadtbezirk, "new");

            final Listenname updateEntity = new Listenname(KURZBEZ, BEZEICHNUNG, List.of(newAssignment));

            when(listennameRepository.findById(KURZBEZ))
                    .thenReturn(Optional.of(foundEntity));
            when(listennameRepository.update(foundEntity))
                    .thenReturn(foundEntity);

            // When
            final Listenname result = unitUnderTest.updateListenname(updateEntity, KURZBEZ);

            // Then
            verify(listennameRepository, times(1)).findById(KURZBEZ);
            verify(listennameRepository, times(1)).update(foundEntity);
            assertThat(result).isSameAs(foundEntity);
            assertThat(foundEntity.getBezeichnung()).isEqualTo(BEZEICHNUNG);
            assertThat(foundEntity.getStadtbezirkslisten()).containsExactly(newAssignment);
            assertThat(newAssignment.getListenName()).isSameAs(foundEntity);
            assertThat(newAssignment.getId())
                    .isEqualTo(new StadtbezirkslistePrimaryKey(KURZBEZ, BigDecimal.ONE));
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final Listenname entity = new Listenname(KURZBEZ, BEZEICHNUNG, List.of());

            when(listennameRepository.findById(KURZBEZ))
                    .thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.updateListenname(entity, KURZBEZ));

            // Then
            verify(listennameRepository, times(1)).findById(KURZBEZ);
            verify(listennameRepository, never()).update(entity);
            assertThat(exception.getMessage())
                    .isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", KURZBEZ));
        }
    }

    @Nested
    class DeleteListenname {

        @Test
        void givenKurzbezExists_thenDeleteEntity() {
            // Given
            Listenname listenname = new Listenname();

            when(stadtbezirkslisteRepository.existsByListenNameKurzbez(KURZBEZ))
                    .thenReturn(false);

            when(listennameRepository.findById(KURZBEZ))
                    .thenReturn(Optional.of(listenname));

            // When
            unitUnderTest.deleteListenname(KURZBEZ);

            // Then
            verify(stadtbezirkslisteRepository)
                    .existsByListenNameKurzbez(KURZBEZ);

            verify(listennameRepository)
                    .findById(KURZBEZ);

            verify(listennameRepository)
                    .delete(listenname);
        }

        @Test
        void givenKurzbezNotExists_thenThrowNotFoundException() {
            // Given
            when(listennameRepository.findById(KURZBEZ))
                    .thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.deleteListenname(KURZBEZ));

            // Then
            verify(listennameRepository, times(1)).findById(KURZBEZ);
            verify(listennameRepository, never()).deleteById(KURZBEZ);
            assertThat(exception.getMessage())
                    .isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", KURZBEZ));
        }
    }
}
