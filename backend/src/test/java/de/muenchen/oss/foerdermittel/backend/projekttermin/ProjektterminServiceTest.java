package de.muenchen.oss.foerdermittel.backend.projekttermin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class ProjektterminServiceTest {

    private static final Long TERMIN_ID = 1L;
    private static final String PROJNR = "310200";
    private static final String OTHER_PROJNR = "310202";

    @Mock
    private ProjektterminRepository projektterminRepository;

    @Mock
    private ProjektService projektService;

    @InjectMocks
    private ProjektterminService unitUnderTest;

    @Nested
    class GetProjekttermine {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Projekttermin projekttermin1 = createProjekttermin(1L, PROJNR);
            final Projekttermin projekttermin2 = createProjekttermin(2L, OTHER_PROJNR);

            final List<Projekttermin> entities = List.of(projekttermin1, projekttermin2);

            final Page<Projekttermin> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(projektterminRepository.findAll(pageable))
                    .thenReturn(expectedPage);

            // When
            final Page<Projekttermin> result = unitUnderTest.getProjekttermine(pageable);

            // Then
            verify(projektterminRepository, times(1))
                    .findAll(pageable);

            assertThat(result)
                    .isEqualTo(expectedPage);
        }
    }

    @Nested
    class GetProjektterminFormContext {

        @Test
        void givenProjektterminIdsAndProjekte_thenReturnCorrectFormContext() {
            // Given
            final List<Long> terminIds = List.of(1L, 2L, 3L);

            final ProjektFormContextDTO projektDTO1 = new ProjektFormContextDTO(
                    PROJNR,
                    "Test Projekt",
                    "Test Strasse",
                    "11",
                    BigDecimal.valueOf(13));

            final ProjektFormContextDTO projektDTO2 = new ProjektFormContextDTO(
                    OTHER_PROJNR,
                    "Test Projekt 2",
                    "Test Strasse 2",
                    "12",
                    BigDecimal.valueOf(13));

            final List<ProjektFormContextDTO> projekte = List.of(projektDTO1, projektDTO2);

            when(projektterminRepository.findAllProjekttermine()).thenReturn(terminIds);

            when(projektService.getProjektFormContextDTOs()).thenReturn(projekte);

            // When
            final ProjektterminFormContext result = unitUnderTest.getProjektterminFormContext();

            // Then
            verify(projektterminRepository, times(1)).findAllProjekttermine();
            verify(projektService, times(1)).getProjektFormContextDTOs();

            assertThat(result.terminID()).isEqualTo(terminIds);

            assertThat(result.projekte()).containsExactly(projektDTO1, projektDTO2);
        }
    }

    @Nested
    class CreateProjekttermin {

        @Test
        void givenProjektterminAndExistingProjekt_thenInsertProjekttermin() {
            // Given
            final Projekt projekt = new Projekt();
            projekt.setProjnr(PROJNR);

            final Projekttermin projekttermin = createProjekttermin(null, null);

            final Projekttermin expectedProjekttermin = createProjekttermin(TERMIN_ID, PROJNR);
            expectedProjekttermin.setProjekt(projekt);

            when(projektService.getProjekt(PROJNR)).thenReturn(projekt);
            when(projektterminRepository.insert(projekttermin)).thenReturn(expectedProjekttermin);

            // When
            final Projekttermin result = unitUnderTest.createProjekttermin(projekttermin, PROJNR);

            // Then
            verify(projektService, times(1)).getProjekt(PROJNR);
            verify(projektterminRepository, times(1)).insert(projekttermin);

            assertThat(projekttermin.getProjekt()).isSameAs(projekt);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedProjekttermin);
        }

        @Test
        void givenProjektDoesNotExist_thenThrowEntityNotFoundException() {
            // Given
            final Projekttermin projekttermin = createProjekttermin(null, null);

            final EntityNotFoundException exception = new EntityNotFoundException(
                    "Projekt mit Projektnummer "
                            + PROJNR
                            + " wurde nicht gefunden");

            when(projektService.getProjekt(PROJNR)).thenThrow(exception);

            // When
            final EntityNotFoundException result = Assertions.assertThrows(
                    EntityNotFoundException.class,
                    () -> unitUnderTest.createProjekttermin(
                            projekttermin,
                            PROJNR));

            // Then
            verify(projektService, times(1)).getProjekt(PROJNR);
            verify(projektterminRepository, never()).insert(any(Projekttermin.class));

            assertThat(result.getMessage()).isEqualTo("Projekt mit Projektnummer " + PROJNR + " wurde nicht gefunden");
        }
    }

    @Nested
    class UpdateProjekttermin {

        @Test
        void givenProjektterminExists_thenUpdateProjekttermin() {
            // Given
            final Projekt existingProjekt = new Projekt();
            existingProjekt.setProjnr(PROJNR);

            final Projekttermin foundProjekttermin = createProjekttermin(TERMIN_ID, PROJNR);
            foundProjekttermin.setProjekt(existingProjekt);

            final Projekttermin projektterminToUpdate = createProjekttermin(null, null);

            final Projekttermin expectedProjekttermin = createProjekttermin(TERMIN_ID, PROJNR);
            expectedProjekttermin.setProjekt(existingProjekt);

            when(projektterminRepository.findById(TERMIN_ID))
                    .thenReturn(Optional.of(foundProjekttermin));

            when(projektterminRepository.update(foundProjekttermin))
                    .thenReturn(expectedProjekttermin);

            // When
            final Projekttermin result = unitUnderTest.updateProjekttermin(
                    projektterminToUpdate,
                    TERMIN_ID);

            // Then
            verify(projektterminRepository, times(1)).findById(TERMIN_ID);
            verify(projektService, never()).getProjekt(any());
            verify(projektterminRepository, times(1)).update(foundProjekttermin);

            assertThat(foundProjekttermin.getZustaendig()).isEqualTo(projektterminToUpdate.getZustaendig());
            assertThat(foundProjekttermin.getUeberwachung()).isEqualTo(projektterminToUpdate.getUeberwachung());
            assertThat(foundProjekttermin.getTelefon()).isEqualTo(projektterminToUpdate.getTelefon());
            assertThat(foundProjekttermin.getTermin()).isEqualTo(projektterminToUpdate.getTermin());
            assertThat(foundProjekttermin.getNotizen()).isEqualTo(projektterminToUpdate.getNotizen());
            assertThat(foundProjekttermin.getProjekt()).isSameAs(existingProjekt);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedProjekttermin);
        }

        @Test
        void givenProjektterminDoesNotExist_thenThrowNotFoundException() {
            // Given
            final Projekttermin projekttermin = createProjekttermin(null, null);

            when(projektterminRepository.findById(TERMIN_ID)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.updateProjekttermin(
                            projekttermin,
                            TERMIN_ID));

            // Then
            verify(projektterminRepository, times(1)).findById(TERMIN_ID);
            verify(projektService, never()).getProjekt(any());
            verify(projektterminRepository, never()).update(any(Projekttermin.class));

            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", TERMIN_ID));
        }

        @Test
        void givenProjektterminWithoutProjekt_thenUpdateWithoutChangingProjekt() {
            // Given
            final Projekt existingProjekt = new Projekt();
            existingProjekt.setProjnr(PROJNR);

            final Projekttermin foundProjekttermin = createProjekttermin(TERMIN_ID, PROJNR);
            foundProjekttermin.setProjekt(existingProjekt);

            final Projekttermin projektterminToUpdate = createProjekttermin(null, null);

            projektterminToUpdate.setProjekt(null);

            when(projektterminRepository.findById(TERMIN_ID)).thenReturn(Optional.of(foundProjekttermin));
            when(projektterminRepository.update(foundProjekttermin)).thenReturn(foundProjekttermin);

            // When
            final Projekttermin result = unitUnderTest.updateProjekttermin(projektterminToUpdate, TERMIN_ID);

            // Then
            verify(projektterminRepository, times(1)).findById(TERMIN_ID);
            verify(projektService, never()).getProjekt(any());
            verify(projektterminRepository, times(1)).update(foundProjekttermin);

            assertThat(foundProjekttermin.getProjekt()).isSameAs(existingProjekt);
            assertThat(result).isSameAs(foundProjekttermin);
        }
    }

    @Nested
    class DeleteProjekttermin {

        @Test
        void givenProjektterminExists_thenDeleteProjekttermin() {
            // Given
            final Projekttermin projekttermin = createProjekttermin(TERMIN_ID, PROJNR);

            when(projektterminRepository.findById(TERMIN_ID)).thenReturn(Optional.of(projekttermin));

            // When
            unitUnderTest.deleteProjekttermin(TERMIN_ID);

            // Then
            verify(projektterminRepository, times(1)).findById(TERMIN_ID);
            verify(projektterminRepository, times(1)).delete(projekttermin);
        }

        @Test
        void givenProjektterminDoesNotExist_thenThrowNotFoundException() {
            // Given
            when(projektterminRepository.findById(TERMIN_ID)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.deleteProjekttermin(TERMIN_ID));

            // Then
            verify(projektterminRepository, times(1)).findById(TERMIN_ID);
            verify(projektterminRepository, never()).delete(any(Projekttermin.class));

            assertThat(exception.getMessage())
                    .isEqualTo(
                            String.format(
                                    "404 NOT_FOUND \"Could not find entity with ID %s\"",
                                    TERMIN_ID));
        }
    }

    private static Projekttermin createProjekttermin(
            final Long id,
            final String projnr) {

        final Projekttermin projekttermin = new Projekttermin();

        projekttermin.setId(id);
        projekttermin.setTermin(LocalDate.of(2026, 1, 10));
        projekttermin.setUeberwachung(true);
        projekttermin.setZustaendig("Max Mustermann");
        projekttermin.setTelefon("12345678");
        projekttermin.setNotizen("Projekttermin Test");

        if (projnr != null) {
            final Projekt projekt = new Projekt();
            projekt.setProjnr(projnr);
            projekttermin.setProjekt(projekt);
        }

        return projekttermin;
    }
}
