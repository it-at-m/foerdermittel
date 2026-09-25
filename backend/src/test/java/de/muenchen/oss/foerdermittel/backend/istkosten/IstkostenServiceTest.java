package de.muenchen.oss.foerdermittel.backend.istkosten;

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
class IstkostenServiceTest {

    private static final BigDecimal ISTKOSTEN_JAHR = new BigDecimal(2026);
    private static final BigDecimal ISTKOSTEN_MONAT = new BigDecimal(10);
    private static final String PROJNR = "3325101";
    private static final String OTHER_PROJNR = "1234567";
    private static final IstkostenPrimaryKey ISTKOSTEN_ID = new IstkostenPrimaryKey(PROJNR,ISTKOSTEN_JAHR, ISTKOSTEN_MONAT);
    private static final IstkostenPrimaryKey OTHER_ISKOSTEN_ID = new IstkostenPrimaryKey(OTHER_PROJNR,ISTKOSTEN_JAHR, ISTKOSTEN_MONAT);

    @Mock
    private IstkostenRepository istkostenRepository;

    @Mock
    private ProjektService projektService;

    @InjectMocks
    private IstkostenService unitUnderTest;

    @Nested
    class GetIstkosteneintraege {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Istkosten istkosten1 = createIstkosten(ISTKOSTEN_ID, PROJNR);
            final Istkosten istkosten2 = createIstkosten(OTHER_ISKOSTEN_ID, OTHER_PROJNR);

            final List<Istkosten> entities = List.of(istkosten1, istkosten2);

            final Page<Istkosten> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(istkostenRepository.findAll(pageable))
                    .thenReturn(expectedPage);

            // When
            final Page<Istkosten> result = unitUnderTest.getIstkostenEintraege(pageable);

            // Then
            verify(istkostenRepository, times(1))
                    .findAll(pageable);

            assertThat(result)
                    .isEqualTo(expectedPage);
        }
    }

    @Nested
    class GetIstkostenFormContext {

        @Test
        void givenIstkostenIdsAndProjekte_thenReturnCorrectFormContext() {
            // Given
            final List<IstkostenPrimaryKey> istkostenIds = List.of(ISTKOSTEN_ID,OTHER_ISKOSTEN_ID);

            final ProjektFormContextDTO projektDTO1 = new ProjektFormContextDTO(
                    PROJNR,
                    "Test Projekt",
                    "Test Strasse",
                    "11");

            final ProjektFormContextDTO projektDTO2 = new ProjektFormContextDTO(
                    OTHER_PROJNR,
                    "Test Projekt 2",
                    "Test Strasse 2",
                    "12");

            final List<ProjektFormContextDTO> projekte = List.of(projektDTO1, projektDTO2);

            when(istkostenRepository.findAllWithProjekt()).thenReturn(istkostenIds);

            when(projektService.getProjektFormContextDTOs()).thenReturn(projekte);

            // When
            final IstkostenFormContext result = unitUnderTest.getIstkostenFormContext();

            // Then
            verify(istkostenRepository, times(1)).findAllWithProjekt();
            verify(projektService, times(1)).getProjektFormContextDTOs();

            assertThat(result.istkosten()).isEqualTo(istkostenIds);

            assertThat(result.projekte()).containsExactly(projektDTO1, projektDTO2);
        }
    }

    @Nested
    class CreateIstkosten {

        @Test
        void givenIstkostenAndExistingProjekt_thenInsertIstkosten() {
            // Given
            final Projekt projekt = new Projekt();
            projekt.setProjnr(PROJNR);

            final Istkosten istkosten = createIstkosten(null, null);

            final Istkosten expectedIstkosten = createIstkosten(ISTKOSTEN_ID, PROJNR);
            expectedIstkosten.setProjekt(projekt);

            when(projektService.getProjekt(PROJNR)).thenReturn(projekt);
            when(istkostenRepository.insert(istkosten)).thenReturn(expectedIstkosten);

            // When
            final Istkosten result = unitUnderTest.createIstkosten(istkosten, PROJNR);

            // Then
            verify(projektService, times(1)).getProjekt(PROJNR);
            verify(istkostenRepository, times(1)).insert(istkosten);

            assertThat(istkosten.getProjekt()).isSameAs(projekt);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedIstkosten);
        }

        @Test
        void givenProjektDoesNotExist_thenThrowEntityNotFoundException() {
            // Given
            final Istkosten istkosten = createIstkosten(null, null);

            final EntityNotFoundException exception = new EntityNotFoundException(
                    "Projekt mit Projektnummer "
                            + PROJNR
                            + " wurde nicht gefunden");

            when(projektService.getProjekt(PROJNR)).thenThrow(exception);

            // When
            final EntityNotFoundException result = Assertions.assertThrows(
                    EntityNotFoundException.class,
                    () -> unitUnderTest.createIstkosten(
                            istkosten,
                            PROJNR));

            // Then
            verify(projektService, times(1)).getProjekt(PROJNR);
            verify(istkostenRepository, never()).insert(any(Istkosten.class));

            assertThat(result.getMessage()).isEqualTo("Projekt mit Projektnummer " + PROJNR + " wurde nicht gefunden");
        }
    }

    @Nested
    class UpdateIstkosten {

        @Test
        void givenIstkostenExists_thenReturnEntity() {
            // Given
            final Projekt existingProjekt = new Projekt();
            existingProjekt.setProjnr(PROJNR);

            final Istkosten foundIstkosten = createIstkosten(ISTKOSTEN_ID, PROJNR);
            foundIstkosten.setProjekt(existingProjekt);

            final Istkosten istkostenToUpdate = createIstkosten(null, null);

            final Istkosten expectedIstkosten = createIstkosten(ISTKOSTEN_ID, PROJNR);
            expectedIstkosten.setProjekt(existingProjekt);

            when(istkostenRepository.findById(ISTKOSTEN_ID))
                    .thenReturn(Optional.of(foundIstkosten));

            when(istkostenRepository.update(foundIstkosten))
                    .thenReturn(expectedIstkosten);

            // When
            final Istkosten result = unitUnderTest.updateIstkosten(
                    istkostenToUpdate,
                    ISTKOSTEN_ID);

            // Then
            verify(istkostenRepository, times(1)).findById(ISTKOSTEN_ID);
            verify(projektService, never()).getProjekt(any());
            verify(istkostenRepository, times(1)).update(foundIstkosten);
            assertThat(result).usingRecursiveComparison().isEqualTo(expectedIstkosten);
        }

        @Test
        void givenIstkostenDoesNotExist_thenThrowNotFoundException() {
            // Given
            final Istkosten istkosten = createIstkosten(null, null);

            when(istkostenRepository.findById(ISTKOSTEN_ID)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.updateIstkosten(
                            istkosten,
                            ISTKOSTEN_ID));

            // Then
            verify(istkostenRepository, times(1)).findById(ISTKOSTEN_ID);
            verify(projektService, never()).getProjekt(any());
            verify(istkostenRepository, never()).update(any(Istkosten.class));

            assertThat(exception.getMessage()).isEqualTo("The Istkosten with ID " + ISTKOSTEN_ID + " was not found.");
        }

        @Test
        void givenIstkostenWithoutProjekt_thenUpdateWithoutChangingProjekt() {
            // Given
            final Projekt existingProjekt = new Projekt();
            existingProjekt.setProjnr(PROJNR);

            final Istkosten foundIstkosten = createIstkosten(ISTKOSTEN_ID, PROJNR);
            foundIstkosten.setProjekt(existingProjekt);

            final Istkosten istkostenToUpdate = createIstkosten(null, null);

            istkostenToUpdate.setProjekt(null);

            when(istkostenRepository.findById(ISTKOSTEN_ID)).thenReturn(Optional.of(foundIstkosten));
            when(istkostenRepository.update(foundIstkosten)).thenReturn(foundIstkosten);

            // When
            final Istkosten result = unitUnderTest.updateIstkosten(istkostenToUpdate, ISTKOSTEN_ID);

            // Then
            verify(istkostenRepository, times(1)).findById(ISTKOSTEN_ID);
            verify(projektService, never()).getProjekt(any());
            verify(istkostenRepository, times(1)).update(foundIstkosten);

            assertThat(foundIstkosten.getProjekt()).isSameAs(existingProjekt);
            assertThat(result).isSameAs(foundIstkosten);
        }
    }

    @Nested
    class DeleteIstkosten {

        @Test
        void givenIstkostenExists_thenDeleteIstkosten() {
            // Given
            final Istkosten istkosten = createIstkosten(ISTKOSTEN_ID, PROJNR);

            when(istkostenRepository.findById(ISTKOSTEN_ID)).thenReturn(Optional.of(istkosten));

            // When
            unitUnderTest.deleteIstkosten(ISTKOSTEN_ID);

            // Then
            verify(istkostenRepository, times(1)).findById(ISTKOSTEN_ID);
            verify(istkostenRepository, times(1)).delete(istkosten);
        }

        @Test
        void givenIstkostenDoesNotExist_thenThrowNotFoundException() {
            // Given
            when(istkostenRepository.findById(ISTKOSTEN_ID)).thenReturn(Optional.empty());

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> unitUnderTest.deleteIstkosten(ISTKOSTEN_ID));

            // Then
            verify(istkostenRepository, times(1)).findById(ISTKOSTEN_ID);
            verify(istkostenRepository, never()).delete(any(Istkosten.class));

            assertThat(exception.getMessage())
                    .isEqualTo("The Istkosten with ID " + ISTKOSTEN_ID + " was not found.");
        }
    }

    private static Istkosten createIstkosten(
            final IstkostenPrimaryKey id,
            final String projnr) {

        final Istkosten istkosten = new Istkosten();

        istkosten.setId(id);
//        istkosten.getId().setJahr(id.getJahr());
//        istkosten.getId().setMonat(id.getMonat());
        istkosten.setIstkosten(new BigDecimal(5000));

        if (projnr != null) {
            final Projekt projekt = new Projekt();
            projekt.setProjnr(projnr);
            istkosten.setProjekt(projekt);
        }

        return istkosten;
    }
}
