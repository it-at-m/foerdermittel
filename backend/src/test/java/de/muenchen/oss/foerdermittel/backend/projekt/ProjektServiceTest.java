package de.muenchen.oss.foerdermittel.backend.projekt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportProjektuebersichtFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private ProjektService projektService;

    @Test
    void givenProjects_thenMapsTheirReportFormContext() {
        // Given
        final List<Projekt> projekte = List.of(mock(Projekt.class));
        final List<ReportProjektuebersichtFormContextDTO> expected = List
                .of(mock(ReportProjektuebersichtFormContextDTO.class));

        when(projektRepository.findAllAsBasic()).thenReturn(List.of());
        when(projektMapper.toReportProjektuebersichtFormContext(List.of())).thenReturn(expected);

        // When
        final var result = projektService.getReportProjektuebersichtFormContextDTOs();

        // Then
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
        final Projekt result = projektService.getProjekt(projnr);

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
        assertThatThrownBy(() -> projektService.getProjekt(projnr))
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
        final List<ProjektFormContextDTO> result = projektService.getProjektFormContextDTOs();

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
        final ProjektFormContext result = projektService.getProjektFormContext();

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
