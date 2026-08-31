package de.muenchen.oss.foerdermittel.backend.archiv;

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
class ArchivServiceTest {

    private static final Long ARCHIV_ID = 1L;
    private static final String PROJNR = "3325101";
    private static final String OTHER_PROJNR = "1234567";

    @Mock
    private ArchivRepository archivRepository;

    @Mock
    private ProjektService projektService;

    @InjectMocks
    private ArchivService unitUnderTest;

    @Nested
    class GetArchiveintraege {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Archiv archiv1 = createArchiv(1L, PROJNR);
            final Archiv archiv2 = createArchiv(2L, OTHER_PROJNR);

            final List<Archiv> entities = List.of(archiv1, archiv2);

            final Page<Archiv> expectedPage =
                    new PageImpl<>(entities, pageable, entities.size());

            when(archivRepository.findAll(pageable))
                    .thenReturn(expectedPage);

            // When
            final Page<Archiv> result =
                    unitUnderTest.getArchiveintraege(pageable);

            // Then
            verify(archivRepository, times(1))
                    .findAll(pageable);

            assertThat(result)
                    .isEqualTo(expectedPage);
        }
    }

    @Nested
    class GetArchivFormContext {

        @Test
        void givenArchivIdsAndProjekte_thenReturnCorrectFormContext() {
            // Given
            final List<Long> archivIds = List.of(1L, 2L, 3L);

            final ProjektFormContextDTO projektDTO1 =
                    new ProjektFormContextDTO(
                            PROJNR,
                            "Test Projekt",
                            "Test Strasse",
                            "11");

            final ProjektFormContextDTO projektDTO2 =
                    new ProjektFormContextDTO(
                            OTHER_PROJNR,
                            "Test Projekt 2",
                            "Test Strasse 2",
                            "12");

            final List<ProjektFormContextDTO> projekte = List.of(projektDTO1, projektDTO2);

            when(archivRepository.findAllWithProjekt()).thenReturn(archivIds);

            when(projektService.getProjektFormContextDTOs()).thenReturn(projekte);

            // When
            final ArchivFormContext result = unitUnderTest.getArchivFormContext();

            // Then
            verify(archivRepository, times(1)).findAllWithProjekt();
            verify(projektService, times(1)).getProjektFormContextDTOs();

            assertThat(result.archivId()).isEqualTo(archivIds);

            assertThat(result.projekte()).containsExactly(projektDTO1, projektDTO2);
        }
    }

    @Nested
    class CreateArchiv {

        @Test
        void givenArchivAndExistingProjekt_thenInsertArchiv() {
            // Given
            final Projekt projekt = new Projekt();
            projekt.setProjnr(PROJNR);

            final Archiv archiv =
                    createArchiv(null, null);

            final Archiv expectedArchiv = createArchiv(ARCHIV_ID, PROJNR);
            expectedArchiv.setProjekt(projekt);

            when(projektService.getProjekt(PROJNR)).thenReturn(projekt);
            when(archivRepository.insert(archiv)).thenReturn(expectedArchiv);

            // When
            final Archiv result = unitUnderTest.createArchiv(archiv, PROJNR);

            // Then
            verify(projektService, times(1)).getProjekt(PROJNR);
            verify(archivRepository, times(1)).insert(archiv);

            assertThat(archiv.getProjekt()).isSameAs(projekt);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedArchiv);
        }

        @Test
        void givenProjektDoesNotExist_thenThrowEntityNotFoundException() {
            // Given
            final Archiv archiv =
                    createArchiv(null, null);

            final EntityNotFoundException exception =
                    new EntityNotFoundException(
                            "Projekt mit Projektnummer "
                                    + PROJNR
                                    + " wurde nicht gefunden");

            when(projektService.getProjekt(PROJNR)).thenThrow(exception);

            // When
            final EntityNotFoundException result =
                    Assertions.assertThrows(
                            EntityNotFoundException.class,
                            () -> unitUnderTest.createArchiv(
                                    archiv,
                                    PROJNR));

            // Then
            verify(projektService, times(1)).getProjekt(PROJNR);
            verify(archivRepository, never()).insert(any(Archiv.class));

            assertThat(result.getMessage()).isEqualTo("Projekt mit Projektnummer " + PROJNR + " wurde nicht gefunden");
        }
    }

    @Nested
    class UpdateArchiv {

        @Test
        void givenArchivExists_thenUpdateArchiv() {
            // Given
            final Projekt existingProjekt = new Projekt();
            existingProjekt.setProjnr(PROJNR);

            final Archiv foundArchiv =
                    createArchiv(ARCHIV_ID, PROJNR);
            foundArchiv.setProjekt(existingProjekt);

            final Archiv archivToUpdate =
                    createArchiv(null, null);

            final Archiv expectedArchiv =
                    createArchiv(ARCHIV_ID, PROJNR);
            expectedArchiv.setProjekt(existingProjekt);

            when(archivRepository.findById(ARCHIV_ID))
                    .thenReturn(Optional.of(foundArchiv));

            when(archivRepository.update(foundArchiv))
                    .thenReturn(expectedArchiv);

            // When
            final Archiv result =
                    unitUnderTest.updateArchiv(
                            archivToUpdate,
                            ARCHIV_ID);

            // Then
            verify(archivRepository, times(1)).findById(ARCHIV_ID);
            verify(projektService, never()).getProjekt(any());
            verify(archivRepository, times(1)).update(foundArchiv);

            assertThat(foundArchiv.getSpeicherDatum()).isEqualTo(archivToUpdate.getSpeicherDatum());
            assertThat(foundArchiv.getSpeicherAkt()).isEqualTo(archivToUpdate.getSpeicherAkt());
            assertThat(foundArchiv.getSpeicherRechnungen()).isEqualTo(archivToUpdate.getSpeicherRechnungen());
            assertThat(foundArchiv.getMikroDatPlan()).isEqualTo(archivToUpdate.getMikroDatPlan());
            assertThat(foundArchiv.getMikroDat()).isEqualTo(archivToUpdate.getMikroDat());
            assertThat(foundArchiv.getNotizen()).isEqualTo(archivToUpdate.getNotizen());
            assertThat(foundArchiv.getProjekt()).isSameAs(existingProjekt);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedArchiv);
        }

        @Test
        void givenArchivDoesNotExist_thenThrowNotFoundException() {
            // Given
            final Archiv archiv = createArchiv(null, null);

            when(archivRepository.findById(ARCHIV_ID)).thenReturn(Optional.empty());

            // When
            final Exception exception =
                    Assertions.assertThrows(
                            NotFoundException.class,
                            () -> unitUnderTest.updateArchiv(
                                    archiv,
                                    ARCHIV_ID));

            // Then
            verify(archivRepository, times(1)).findById(ARCHIV_ID);
            verify(projektService, never()).getProjekt(any());
            verify(archivRepository, never()).update(any(Archiv.class));

            assertThat(exception.getMessage()).isEqualTo(String.format("404 NOT_FOUND \"Could not find entity with ID %s\"", ARCHIV_ID));
        }

        @Test
        void givenArchivWithoutProjekt_thenUpdateWithoutChangingProjekt() {
            // Given
            final Projekt existingProjekt = new Projekt();
            existingProjekt.setProjnr(PROJNR);

            final Archiv foundArchiv = createArchiv(ARCHIV_ID, PROJNR);
            foundArchiv.setProjekt(existingProjekt);

            final Archiv archivToUpdate = createArchiv(null, null);

            archivToUpdate.setProjekt(null);

            when(archivRepository.findById(ARCHIV_ID)).thenReturn(Optional.of(foundArchiv));
            when(archivRepository.update(foundArchiv)).thenReturn(foundArchiv);

            // When
            final Archiv result = unitUnderTest.updateArchiv(archivToUpdate, ARCHIV_ID);

            // Then
            verify(archivRepository, times(1)).findById(ARCHIV_ID);
            verify(projektService, never()).getProjekt(any());
            verify(archivRepository, times(1)).update(foundArchiv);

            assertThat(foundArchiv.getProjekt()).isSameAs(existingProjekt);
            assertThat(result).isSameAs(foundArchiv);
        }
    }

    @Nested
    class DeleteArchiv {

        @Test
        void givenArchivExists_thenDeleteArchiv() {
            // Given
            final Archiv archiv = createArchiv(ARCHIV_ID, PROJNR);

            when(archivRepository.findById(ARCHIV_ID)).thenReturn(Optional.of(archiv));

            // When
            unitUnderTest.deleteArchiv(ARCHIV_ID);

            // Then
            verify(archivRepository, times(1)).findById(ARCHIV_ID);
            verify(archivRepository, times(1)).delete(archiv);
        }

        @Test
        void givenArchivDoesNotExist_thenThrowNotFoundException() {
            // Given
            when(archivRepository.findById(ARCHIV_ID)).thenReturn(Optional.empty());

            // When
            final Exception exception =
                    Assertions.assertThrows(
                            NotFoundException.class,
                            () -> unitUnderTest.deleteArchiv(ARCHIV_ID));

            // Then
            verify(archivRepository, times(1)).findById(ARCHIV_ID);
            verify(archivRepository, never()).delete(any(Archiv.class));

            assertThat(exception.getMessage())
                    .isEqualTo(
                            String.format(
                                    "404 NOT_FOUND \"Could not find entity with ID %s\"",
                                    ARCHIV_ID));
        }
    }

    private static Archiv createArchiv(
            final Long id,
            final String projnr) {

        final Archiv archiv = new Archiv();

        archiv.setId(id);
        archiv.setSpeicherDatum(LocalDate.of(2026, 1, 10));
        archiv.setSpeicherAkt(true);
        archiv.setSpeicherRechnungen(false);
        archiv.setMikroDatPlan(LocalDate.of(2026, 2, 10));
        archiv.setMikroDat(LocalDate.of(2026, 3, 10));
        archiv.setNotizen("Archiv Test");

        if (projnr != null) {
            final Projekt projekt = new Projekt();
            projekt.setProjnr(projnr);
            archiv.setProjekt(projekt);
        }

        return archiv;
    }
}
