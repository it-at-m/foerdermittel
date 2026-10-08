package de.muenchen.oss.foerdermittel.backend.termin;

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
public class TerminServiceTest {

    private static final Long TERMIN_ID = 1L;
    private static final String PROJNR = "310200";
    private static final String OTHER_PROJNR = "310202";

    @Mock
    private TerminRepository terminRepository;

    @Mock
    private ProjektService projektService;

    @InjectMocks
    private TerminService unitUnderTest;

    @Nested
    class GetTermin {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Termin termin1 = createTermin(1L, PROJNR);
            final Termin termin2 = createTermin(2L, OTHER_PROJNR);

            final List<Termin> entities = List.of(termin1, termin2);

            final Page<Termin> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(terminRepository.findAll(pageable))
                    .thenReturn(expectedPage);

            // When
            final Page<Termin> result = unitUnderTest.getTermin(pageable);

            // Then
            verify(terminRepository, times(1))
                    .findAll(pageable);

            assertThat(result)
                    .isEqualTo(expectedPage);
        }
    }

    @Nested
    class GetTerminFormContext {

        @Test
        void givenTerminIdsAndProjekte_thenReturnCorrectFormContext() {
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

            when(terminRepository.findAllTermine()).thenReturn(terminIds);

            when(projektService.getProjektFormContextDTOs()).thenReturn(projekte);

            // When
            final TerminFormContext result = unitUnderTest.getTerminFormContext();

            // Then
            verify(terminRepository, times(1)).findAllTermine();
            verify(projektService, times(1)).getProjektFormContextDTOs();

            assertThat(result.terminID()).isEqualTo(terminIds);

            assertThat(result.projekte()).containsExactly(projektDTO1, projektDTO2);
        }
    }

    @Nested
    class CreateTermin {

        @Test
        void givenTerminAndExistingProjekt_thenInsertTermin() {
            // Given
            final Projekt projekt = new Projekt();
            projekt.setProjnr(PROJNR);

            final Termin termin = createTermin(null, null);

            final Termin expectedTermin = createTermin(TERMIN_ID, PROJNR);
            expectedTermin.setProjekt(projekt);

            when(projektService.getProjekt(PROJNR)).thenReturn(projekt);
            when(terminRepository.insert(termin)).thenReturn(expectedTermin);

            // When
            final Termin result = unitUnderTest.createTermin(termin, PROJNR);

            // Then
            verify(projektService, times(1)).getProjekt(PROJNR);
            verify(terminRepository, times(1)).insert(termin);

            assertThat(termin.getProjekt()).isSameAs(projekt);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedTermin);
        }

        @Test
        void givenProjektDoesNotExist_thenThrowEntityNotFoundException() {
            // Given
            final Termin termin = createTermin(null, null);

            final EntityNotFoundException exception = new EntityNotFoundException(
                    "Projekt mit Projektnummer "
                            + PROJNR
                            + " wurde nicht gefunden");

            when(projektService.getProjekt(PROJNR)).thenThrow(exception);

            // When
            final EntityNotFoundException result = Assertions.assertThrows(
                    EntityNotFoundException.class,
                    () -> unitUnderTest.createTermin(
                            termin,
                            PROJNR));

            // Then
            verify(projektService, times(1)).getProjekt(PROJNR);
            verify(terminRepository, never()).insert(any(Termin.class));

            assertThat(result.getMessage()).isEqualTo("Projekt mit Projektnummer " + PROJNR + " wurde nicht gefunden");
        }
    }

    @Nested
    class UpdateTermin {

        @Test
        void givenTerminExists_thenUpdateTermin() {
            // Given
            final Projekt existingProjekt = new Projekt();
            existingProjekt.setProjnr(PROJNR);

            final Termin foundTermin = createTermin(TERMIN_ID, PROJNR);
            foundTermin.setProjekt(existingProjekt);

            final Termin terminToUpdate = createTermin(null, null);

            final Termin expectedTermin = createTermin(TERMIN_ID, PROJNR);
            expectedTermin.setProjekt(existingProjekt);

            when(terminRepository.findById(TERMIN_ID))
                    .thenReturn(Optional.of(foundTermin));

            when(terminRepository.update(foundTermin))
                    .thenReturn(expectedTermin);

            // When
            final Termin result = unitUnderTest.updateTermin(
                    terminToUpdate,
                    TERMIN_ID);

            // Then
            verify(terminRepository, times(1)).findById(TERMIN_ID);
            verify(projektService, never()).getProjekt(any());
            verify(terminRepository, times(1)).update(foundTermin);

            assertThat(foundTermin.getZustaendig()).isEqualTo(terminToUpdate.getZustaendig());
            assertThat(foundTermin.getUeberwachung()).isEqualTo(terminToUpdate.getUeberwachung());
            assertThat(foundTermin.getTelefon()).isEqualTo(terminToUpdate.getTelefon());
            assertThat(foundTermin.getTermin()).isEqualTo(terminToUpdate.getTermin());
            assertThat(foundTermin.getNotizen()).isEqualTo(terminToUpdate.getNotizen());
            assertThat(foundTermin.getProjekt()).isSameAs(existingProjekt);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedTermin);
        }

        @Test
        void givenTerminDoesNotExist_thenThrowNotFoundException() {
            // Given
            final Termin termin = createTermin(null, null);

            when(terminRepository.findById(TERMIN_ID)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.updateTermin(
                            termin,
                            TERMIN_ID));

            // Then
            verify(terminRepository, times(1)).findById(TERMIN_ID);
            verify(projektService, never()).getProjekt(any());
            verify(terminRepository, never()).update(any(Termin.class));

            assertThat(exception.getMessage()).isEqualTo("The Termin with ID " + TERMIN_ID + " was not found.");
        }

        @Test
        void givenTerminWithoutProjekt_thenUpdateWithoutChangingProjekt() {
            // Given
            final Projekt existingProjekt = new Projekt();
            existingProjekt.setProjnr(PROJNR);

            final Termin foundTermin = createTermin(TERMIN_ID, PROJNR);
            foundTermin.setProjekt(existingProjekt);

            final Termin terminToUpdate = createTermin(null, null);

            terminToUpdate.setProjekt(null);

            when(terminRepository.findById(TERMIN_ID)).thenReturn(Optional.of(foundTermin));
            when(terminRepository.update(foundTermin)).thenReturn(foundTermin);

            // When
            final Termin result = unitUnderTest.updateTermin(terminToUpdate, TERMIN_ID);

            // Then
            verify(terminRepository, times(1)).findById(TERMIN_ID);
            verify(projektService, never()).getProjekt(any());
            verify(terminRepository, times(1)).update(foundTermin);

            assertThat(foundTermin.getProjekt()).isSameAs(existingProjekt);
            assertThat(result).isSameAs(foundTermin);
        }
    }

    @Nested
    class DeleteTermin {

        @Test
        void givenTerminExists_thenDeleteTermin() {
            // Given
            final Termin termin = createTermin(TERMIN_ID, PROJNR);

            when(terminRepository.findById(TERMIN_ID)).thenReturn(Optional.of(termin));

            // When
            unitUnderTest.deleteTermin(TERMIN_ID);

            // Then
            verify(terminRepository, times(1)).findById(TERMIN_ID);
            verify(terminRepository, times(1)).delete(termin);
        }

        @Test
        void givenTerminDoesNotExist_thenThrowNotFoundException() {
            // Given
            when(terminRepository.findById(TERMIN_ID)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.deleteTermin(TERMIN_ID));

            // Then
            verify(terminRepository, times(1)).findById(TERMIN_ID);
            verify(terminRepository, never()).delete(any(Termin.class));

            assertThat(exception.getMessage()).isEqualTo("The Termin with ID " + TERMIN_ID + " was not found.");
        }
    }

    private static Termin createTermin(
            final Long id,
            final String projnr) {

        final Termin termin = new Termin();

        termin.setId(id);
        termin.setTermin(LocalDate.of(2026, 1, 10));
        termin.setUeberwachung(true);
        termin.setZustaendig("Max Mustermann");
        termin.setTelefon("12345678");
        termin.setNotizen("Termin Test");

        if (projnr != null) {
            final Projekt projekt = new Projekt();
            projekt.setProjnr(projnr);
            termin.setProjekt(projekt);
        }

        return termin;
    }
}
