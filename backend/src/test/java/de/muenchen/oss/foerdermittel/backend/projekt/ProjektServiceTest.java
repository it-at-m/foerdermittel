package de.muenchen.oss.foerdermittel.backend.projekt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektCreateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportBewilligungAuszahlungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportProjektuebersichtFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
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
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class ProjektServiceTest {

    @Mock
    private ProjektRepository projektRepository;

    @Mock
    private ProjektMapper projektMapper;

    @Mock
    private FoerderbereichService foerderbereichService;

    @Mock
    private StadtbezirkService stadtbezirkService;

    @Mock
    private UnterabschnittService unterabschnittService;

    @Mock
    private KurzbezeichnungService kurzbezeichnungService;

    @Mock
    private BauprogrammService bauprogrammService;

    @Mock
    private SiedlungsgebietService siedlungsgebietService;

    @Mock
    private KrankenhausService krankenhausService;

    @InjectMocks
    private ProjektService unitUnderTest;

    @Nested
    class GetProjekt {

        @Test
        void givenIdExists_thenReturnEntity() {
            final String id = "1234567";
            final Projekt expectedEntity = mock(Projekt.class);

            when(projektRepository.findById(id)).thenReturn(Optional.of(expectedEntity));

            final Projekt result = unitUnderTest.getProjekt(id);

            verify(projektRepository, times(1)).findById(id);
            assertThat(result).isSameAs(expectedEntity);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            final String id = "MISSING";

            when(projektRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> unitUnderTest.getProjekt(id))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("The Projekt with ID MISSING was not found.");

            verify(projektRepository, times(1)).findById(id);
        }
    }

    @Nested
    class GetProjekte {

        @Test
        void givenFilterAndPageable_thenReturnPageOfEntities() {
            final ProjektFilter filter = mock(ProjektFilter.class);
            final Pageable pageable = PageRequest.of(0, 10);
            final List<Projekt> entities = List.of(mock(Projekt.class));
            final Page<Projekt> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(projektRepository.findAll(
                    Mockito.<Specification<Projekt>>any(), Mockito.eq(pageable)))
                    .thenReturn(expectedPage);

            final Page<Projekt> result = unitUnderTest.getProjekte(filter, pageable);

            verify(projektRepository, times(1)).findAll(
                    Mockito.<Specification<Projekt>>any(), Mockito.eq(pageable));
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class GetProjektFormContextDTOs {

        @Test
        void givenProjects_thenReturnFormContextDTOs() {
            final List<Projekt> entities = List.of(mock(Projekt.class));
            final List<ProjektFormContextDTO> expectedDTOs = List.of(mock(ProjektFormContextDTO.class));

            when(projektRepository.findAll()).thenReturn(entities);
            when(projektMapper.toFormContext(entities)).thenReturn(expectedDTOs);

            final List<ProjektFormContextDTO> result = unitUnderTest.getProjektFormContextDTOs();

            verify(projektRepository, times(1)).findAll();
            verify(projektMapper, times(1)).toFormContext(entities);
            assertThat(result).isEqualTo(expectedDTOs);
        }
    }

    @Nested
    class GetProjektFormContext {

        @Test
        void givenFormContextData_thenReturnCorrectFormContext() {
            final List<Projekt> projekte = List.of(mock(Projekt.class));
            final List<ProjektFormContextDTO> projektDTOs = List.of(mock(ProjektFormContextDTO.class));
            final List<FoerderbereichFormContextDTO> fbs = List.of(mock(FoerderbereichFormContextDTO.class));
            final List<StadtbezirkFormContextDTO> bezs = List.of(mock(StadtbezirkFormContextDTO.class));
            final List<UnterabschnittFormContextDTO> uas = List.of(mock(UnterabschnittFormContextDTO.class));
            final List<KurzbezeichnungFormContextDTO> kurzs = List.of(mock(KurzbezeichnungFormContextDTO.class));
            final List<BauprogrammFormContextDTO> bpgs = List.of(mock(BauprogrammFormContextDTO.class));
            final List<SiedlungsgebietFormContextDTO> sgts = List.of(mock(SiedlungsgebietFormContextDTO.class));
            final List<KrankenhausFormContextDTO> krhs = List.of(mock(KrankenhausFormContextDTO.class));

            when(projektRepository.findAll()).thenReturn(projekte);
            when(projektMapper.toFormContext(projekte)).thenReturn(projektDTOs);
            when(foerderbereichService.getFoerderbereichFormContextDTOs()).thenReturn(fbs);
            when(stadtbezirkService.getStadtbezirkFormContextDTOs()).thenReturn(bezs);
            when(unterabschnittService.getUnterabschnittFormContextDTOs()).thenReturn(uas);
            when(kurzbezeichnungService.getKurzbezeichnungFormContextDTOs()).thenReturn(kurzs);
            when(bauprogrammService.getBauprogrammFormContextDTOs()).thenReturn(bpgs);
            when(siedlungsgebietService.getSiedlungsgebietFormContextDTOs()).thenReturn(sgts);
            when(krankenhausService.getKrankenhausFormContextDTOs()).thenReturn(krhs);

            final ProjektFormContext result = unitUnderTest.getProjektFormContext();

            verify(projektRepository, times(1)).findAll();
            verify(projektMapper, times(1)).toFormContext(projekte);

            assertThat(result.projekte()).isEqualTo(projektDTOs);
            assertThat(result.fbs()).isEqualTo(fbs);
            assertThat(result.bezs()).isEqualTo(bezs);
            assertThat(result.uas()).isEqualTo(uas);
            assertThat(result.kurzs()).isEqualTo(kurzs);
            assertThat(result.bpgs()).isEqualTo(bpgs);
            assertThat(result.sgts()).isEqualTo(sgts);
            assertThat(result.krhs()).isEqualTo(krhs);
            assertThat(result.krisofps()).containsExactly(Krisofp.values());
            assertThat(result.krhzwecks()).containsExactly(Krhzweck.values());
        }
    }

    @Nested
    class GetReportProjektuebersichtFormContextDTOs {

        @Test
        void givenProjects_thenReturnReportFormContextDTOs() {
            final List<ReportProjektuebersichtFormContextDTO> expected = List.of(mock(ReportProjektuebersichtFormContextDTO.class));

            when(projektRepository.findAllAsBasic()).thenReturn(List.of());
            when(projektMapper.toReportProjektuebersichtFormContext(List.of()))
                    .thenReturn(expected);

            final List<ReportProjektuebersichtFormContextDTO> result = unitUnderTest.getReportProjektuebersichtFormContextDTOs();

            // Then
            verify(projektRepository, times(1)).findAllAsBasic();
            verify(projektMapper, times(1))
                    .toReportProjektuebersichtFormContext(List.of());
            assertThat(result).isEqualTo(expected);
        }
    }

    @Nested
    class CreateProjekt {

        @Test
        void givenValidCreateDTO_thenCreatesProjekt() {
            final ProjektCreateDTO dto = new ProjektCreateDTO(
                    "1234567", 1, null, null, "ABC", "UA", null, 26, 0, 1,
                    "Projektname", "Musterstraße 1", "Neubau", null, null,
                    null, null, null, null, null, null, null);
            final Projekt projekt = new Projekt();
            final var foerderbereich = mock(Foerderbereich.class);
            final var kurzbezeichnung = mock(Kurzbezeichnung.class);
            final var unterabschnitt = mock(Unterabschnitt.class);
            when(projektMapper.toEntity(dto)).thenReturn(projekt);
            when(foerderbereichService.getFoerderbereich(BigDecimal.ONE)).thenReturn(foerderbereich);
            when(kurzbezeichnungService.getKurzbezeichnung("ABC")).thenReturn(kurzbezeichnung);
            when(unterabschnittService.getUnterabschnitt("UA")).thenReturn(unterabschnitt);
            when(projektRepository.insert(projekt)).thenReturn(projekt);
            final Projekt result = unitUnderTest.createProjekt(dto);
            verify(projektMapper).toEntity(dto);
            verify(foerderbereichService).getFoerderbereich(BigDecimal.ONE);
            verify(kurzbezeichnungService).getKurzbezeichnung("ABC");
            verify(unterabschnittService).getUnterabschnitt("UA");
            verify(stadtbezirkService, never()).getStadtbezirk(Mockito.any());
            verify(krankenhausService, never()).getKrankenhaus(Mockito.any());
            verify(siedlungsgebietService, never()).getSiedlungsgebiet(Mockito.any());
            verify(bauprogrammService, never()).getBauprogramm(Mockito.any());
            verify(projektRepository).insert(projekt);
            assertThat(result).isSameAs(projekt);
            assertThat(projekt.getFobFb()).isSameAs(foerderbereich);
            assertThat(projekt.getKurKurzbez()).isSameAs(kurzbezeichnung);
            assertThat(projekt.getUasUa()).isSameAs(unterabschnitt);
            assertThat(projekt.getKauf()).isFalse();
            assertThat(projekt.getRefinanzierbar()).isTrue();
        }
    }

    @Nested
    class UpdateProjekt {

        @Test
        void givenUpdateDTO_thenUpdatesProjekt() {
            final ProjektUpdateDTO dto = new ProjektUpdateDTO("Neuer Projektname", null, null, null, null, null, null, null, null, null, null, null, null, null,
                    null, null, null);
            final Projekt projekt = new Projekt();
            final Projekt mappedProjekt = new Projekt();
            mappedProjekt.setPname("Neuer Projektname");
            when(projektRepository.findById("1234567")).thenReturn(Optional.of(projekt));
            when(projektMapper.toEntity(dto)).thenReturn(mappedProjekt);
            when(projektRepository.update(projekt)).thenReturn(projekt);
            final Projekt result = unitUnderTest.updateProjekt(dto, "1234567");
            assertThat(result.getPname()).isEqualTo("Neuer Projektname");
            verify(projektRepository).update(projekt);
        }

        @Test
        void givenProjektNotExists_thenThrowNotFoundException() {
            final String id = "MISSING";
            final ProjektUpdateDTO dto = new ProjektUpdateDTO(
                    null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null);

            when(projektRepository.findById(id)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> unitUnderTest.updateProjekt(dto, id))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("The Projekt with ID MISSING was not found.");

            verify(projektRepository, times(1)).findById(id);
            verify(projektMapper, never()).toEntity(dto);
            verify(projektRepository, never()).update(Mockito.any(Projekt.class));
        }
    }

    @Nested
    class DeleteProjekt {

        @Test
        void givenIdExists_thenDeleteProjekt() {
            final String id = "1234567";

            when(projektRepository.findById(id)).thenReturn(Optional.of(new Projekt()));

            // When
            unitUnderTest.deleteProjekt(id);

            // Then
            verify(projektRepository, times(1)).findById(id);
            verify(projektRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            final String id = "MISSING";

            when(projektRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> unitUnderTest.deleteProjekt(id))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("The Projekt with ID MISSING was not found.");

            verify(projektRepository, times(1)).findById(id);
            verify(projektRepository, never()).deleteById(id);
        }
    }

    @Nested
    class GetReportAuswertungProjektFormContextDTOs {

        @Test
        void givenProjects_thenReturnReportFormContextDTOs() {
            final List<ReportAuswertungProjektFormContextDTO> expected = List.of(mock(ReportAuswertungProjektFormContextDTO.class));

            when(projektRepository.findAllAuswertungProjekte()).thenReturn(List.of());
            when(projektMapper.toReportAuswertungProjektFormContext(List.of()))
                    .thenReturn(expected);

            final List<ReportAuswertungProjektFormContextDTO> result = unitUnderTest.getReportAuswertungProjektFormContextDTOs();

            verify(projektRepository, times(1)).findAllAuswertungProjekte();
            verify(projektMapper, times(1))
                    .toReportAuswertungProjektFormContext(List.of());
            assertThat(result).isEqualTo(expected);
        }
    }

    @Nested
    class GetReportBewilligungAuszahlungProjektFormContextDTOs {

        @Test
        void givenProjects_thenReturnReportFormContextDTOs() {
            final List<ReportBewilligungAuszahlungProjektFormContextDTO> expected = List.of(mock(ReportBewilligungAuszahlungProjektFormContextDTO.class));

            when(projektRepository.findAllAsBasic()).thenReturn(List.of());
            when(projektMapper.toReportBewilligungAuszahlungProjektFormContext(List.of()))
                    .thenReturn(expected);

            final List<ReportBewilligungAuszahlungProjektFormContextDTO> result = unitUnderTest.getReportBewilligungAuszahlungProjektFormContextDTOs();

            verify(projektRepository, times(1)).findAllAsBasic();
            verify(projektMapper, times(1))
                    .toReportBewilligungAuszahlungProjektFormContext(List.of());
            assertThat(result).isEqualTo(expected);
        }
    }

    @Test
    void givenProjects_thenMapsTheirReportFormContext() {
        final List<ReportProjektuebersichtFormContextDTO> expected = List.of(mock(ReportProjektuebersichtFormContextDTO.class));

        when(projektRepository.findAllAsBasic()).thenReturn(List.of());
        when(projektMapper.toReportProjektuebersichtFormContext(List.of())).thenReturn(expected);

        final var result = unitUnderTest.getReportProjektuebersichtFormContextDTOs();

        assertThat(result).isEqualTo(expected);
        verify(projektRepository).findAllAsBasic();
        verify(projektMapper).toReportProjektuebersichtFormContext(List.of());
    }

    @Test
    void givenExistingProjnr_thenReturnsProjekt() {
        // Given
        final String projnr = "1234567";
        final Projekt projekt = mock(Projekt.class);

        when(projektRepository.findById(projnr)).thenReturn(Optional.of(projekt));

        // When
        final Projekt result = unitUnderTest.getProjekt(projnr);

        // Then
        assertThat(result).isSameAs(projekt);
        verify(projektRepository).findById(projnr);
    }

    @Test
    void givenMissingProjnr_thenThrowsNotFoundException() {
        // Given
        final String projnr = "MISSING";

        when(projektRepository.findById(projnr)).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> unitUnderTest.getProjekt(projnr))
                .hasMessage("The Projekt with ID MISSING was not found.");

        verify(projektRepository).findById(projnr);
    }

    @Test
    void givenProjects_thenReturnsProjektFormContextDTOs() {
        // Given
        final List<Projekt> projekte = List.of(mock(Projekt.class));
        final List<ProjektFormContextDTO> expected = List.of(mock(ProjektFormContextDTO.class));

        when(projektRepository.findAll()).thenReturn(projekte);
        when(projektMapper.toFormContext(projekte)).thenReturn(expected);

        // When
        final List<ProjektFormContextDTO> result = unitUnderTest.getProjektFormContextDTOs();

        // Then
        assertThat(result).isEqualTo(expected);
        verify(projektRepository).findAll();
        verify(projektMapper).toFormContext(projekte);
    }

    @Test
    void givenFormContextData_thenReturnsProjektFormContext() {
        // Given
        final List<Projekt> projekte = List.of(mock(Projekt.class));
        final List<ProjektFormContextDTO> projektDTOs = List.of(mock(ProjektFormContextDTO.class));
        final List<FoerderbereichFormContextDTO> fbs = List.of(mock(FoerderbereichFormContextDTO.class));
        final List<StadtbezirkFormContextDTO> bezs = List.of(mock(StadtbezirkFormContextDTO.class));
        final List<UnterabschnittFormContextDTO> uas = List.of(mock(UnterabschnittFormContextDTO.class));
        final List<KurzbezeichnungFormContextDTO> kurzs = List.of(mock(KurzbezeichnungFormContextDTO.class));
        final List<BauprogrammFormContextDTO> bpgs = List.of(mock(BauprogrammFormContextDTO.class));
        final List<SiedlungsgebietFormContextDTO> sgts = List.of(mock(SiedlungsgebietFormContextDTO.class));
        final List<KrankenhausFormContextDTO> krhs = List.of(mock(KrankenhausFormContextDTO.class));

        when(projektRepository.findAll()).thenReturn(projekte);
        when(projektMapper.toFormContext(projekte)).thenReturn(projektDTOs);
        when(foerderbereichService.getFoerderbereichFormContextDTOs()).thenReturn(fbs);
        when(stadtbezirkService.getStadtbezirkFormContextDTOs()).thenReturn(bezs);
        when(unterabschnittService.getUnterabschnittFormContextDTOs()).thenReturn(uas);
        when(kurzbezeichnungService.getKurzbezeichnungFormContextDTOs()).thenReturn(kurzs);
        when(bauprogrammService.getBauprogrammFormContextDTOs()).thenReturn(bpgs);
        when(siedlungsgebietService.getSiedlungsgebietFormContextDTOs()).thenReturn(sgts);
        when(krankenhausService.getKrankenhausFormContextDTOs()).thenReturn(krhs);

        // When
        final ProjektFormContext result = unitUnderTest.getProjektFormContext();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.projekte()).isEqualTo(projektDTOs);
        assertThat(result.fbs()).isEqualTo(fbs);
        assertThat(result.bezs()).isEqualTo(bezs);
        assertThat(result.uas()).isEqualTo(uas);
        assertThat(result.kurzs()).isEqualTo(kurzs);
        assertThat(result.bpgs()).isEqualTo(bpgs);
        assertThat(result.sgts()).isEqualTo(sgts);
        assertThat(result.krhs()).isEqualTo(krhs);
        assertThat(result.krisofps()).containsExactly(Krisofp.values());
        assertThat(result.krhzwecks()).containsExactly(Krhzweck.values());

        verify(projektRepository).findAll();
        verify(projektMapper).toFormContext(projekte);
    }

}
