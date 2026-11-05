package de.muenchen.oss.foerdermittel.backend.projekt.tabs.allgemein;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Krhzweck;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektFormContext;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektCreateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjektAllgemeinServiceTest {

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
    private ProjektAllgemeinService unitUnderTest;

    @Nested
    class GetProjektFormContext {

        @Test
        void givenFormContextData_thenReturnsCorrectFormContext() {
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
            verify(foerderbereichService).getFoerderbereichFormContextDTOs();
            verify(stadtbezirkService).getStadtbezirkFormContextDTOs();
            verify(unterabschnittService).getUnterabschnittFormContextDTOs();
            verify(kurzbezeichnungService).getKurzbezeichnungFormContextDTOs();
            verify(bauprogrammService).getBauprogrammFormContextDTOs();
            verify(siedlungsgebietService).getSiedlungsgebietFormContextDTOs();
            verify(krankenhausService).getKrankenhausFormContextDTOs();
        }
    }

    @Nested
    class GetProjektFormContextDTOs {

        @Test
        void givenProjects_thenReturnsFormContextDTOs() {
            final List<Projekt> projekte = List.of(mock(Projekt.class));
            final List<ProjektFormContextDTO> expected = List.of(mock(ProjektFormContextDTO.class));

            when(projektRepository.findAll()).thenReturn(projekte);
            when(projektMapper.toFormContext(projekte)).thenReturn(expected);

            final List<ProjektFormContextDTO> result = unitUnderTest.getProjektFormContextDTOs();

            assertThat(result).isEqualTo(expected);
            verify(projektRepository).findAll();
            verify(projektMapper).toFormContext(projekte);
        }
    }

    @Nested
    class CreateProjekt {

        @Test
        void givenValidCreateDTO_thenCreatesProjektWithRequiredFields() {
            final ProjektCreateDTO dto = new ProjektCreateDTO(
                    "1234567", 1, null, null, "ABC", "UA", null, 26, 0, 1,
                    "Projektname", "Musterstraße 1", "Neubau", null, null,
                    null, null, null, null, null, null, null);
            final Projekt projekt = new Projekt();
            final Foerderbereich foerderbereich = Mockito.mock(Foerderbereich.class);
            final Kurzbezeichnung kurzbezeichnung = Mockito.mock(Kurzbezeichnung.class);
            final Unterabschnitt unterabschnitt = Mockito.mock(Unterabschnitt.class);

            when(projektMapper.toEntity(dto)).thenReturn(projekt);
            when(foerderbereichService.getFoerderbereich(BigDecimal.ONE)).thenReturn(foerderbereich);
            when(kurzbezeichnungService.getKurzbezeichnung("ABC")).thenReturn(kurzbezeichnung);
            when(unterabschnittService.getUnterabschnitt("UA")).thenReturn(unterabschnitt);

            final Projekt result = unitUnderTest.createProjekt(dto);

            verify(projektMapper).toEntity(dto);
            verify(foerderbereichService).getFoerderbereich(BigDecimal.ONE);
            verify(kurzbezeichnungService).getKurzbezeichnung("ABC");
            verify(unterabschnittService).getUnterabschnitt("UA");
            verify(stadtbezirkService, never()).getStadtbezirk(Mockito.any());
            verify(krankenhausService, never()).getKrankenhaus(Mockito.any());
            verify(siedlungsgebietService, never()).getSiedlungsgebiet(Mockito.any());
            verify(bauprogrammService, never()).getBauprogramm(Mockito.any());

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
        void givenUpdateDTO_thenUpdatesProjektWithoutChangingIgnoredFields() {
            final ProjektUpdateDTO dto = new ProjektUpdateDTO(
                    "Neuer Projektname", null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null);
            final Projekt projekt = new Projekt();
            projekt.setProjnr("1234567");
            projekt.setJahr("26");
            projekt.setLfdnr1("0");
            projekt.setLfdnr2("1");

            final Unterabschnitt unterabschnitt = Mockito.mock(Unterabschnitt.class);
            final Foerderbereich foerderbereich = Mockito.mock(Foerderbereich.class);
            final Kurzbezeichnung kurzbezeichnung = Mockito.mock(Kurzbezeichnung.class);

            projekt.setUasUa(unterabschnitt);
            projekt.setFobFb(foerderbereich);
            projekt.setKurKurzbez(kurzbezeichnung);

            final Projekt mappedProjekt = new Projekt();
            mappedProjekt.setPname("Neuer Projektname");

            when(projektMapper.toEntity(dto)).thenReturn(mappedProjekt);

            final Projekt result = unitUnderTest.updateProjekt(projekt, dto);

            verify(projektMapper).toEntity(dto);
            verify(foerderbereichService, never()).getFoerderbereich(Mockito.any());
            verify(kurzbezeichnungService, never()).getKurzbezeichnung(Mockito.any());
            verify(stadtbezirkService, never()).getStadtbezirk(Mockito.any());
            verify(krankenhausService, never()).getKrankenhaus(Mockito.any());
            verify(siedlungsgebietService, never()).getSiedlungsgebiet(Mockito.any());
            verify(bauprogrammService, never()).getBauprogramm(Mockito.any());

            assertThat(result).isSameAs(projekt);
            assertThat(projekt.getPname()).isEqualTo("Neuer Projektname");
            assertThat(projekt.getProjnr()).isEqualTo("1234567");
            assertThat(projekt.getJahr()).isEqualTo("26");
            assertThat(projekt.getLfdnr1()).isEqualTo("0");
            assertThat(projekt.getLfdnr2()).isEqualTo("1");
            assertThat(projekt.getUasUa()).isSameAs(unterabschnitt);
            assertThat(projekt.getFobFb()).isSameAs(foerderbereich);
            assertThat(projekt.getKurKurzbez()).isSameAs(kurzbezeichnung);
        }
    }
}
