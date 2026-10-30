package de.muenchen.oss.foerdermittel.backend.report.projektuebersichtms;

import static de.muenchen.oss.foerdermittel.backend.projekt.Krisofp.X;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.ReportProjektuebersichtMsService;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsDTO;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsMapper;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsSort;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.formcontext.ReportProjektuebersichtMsFormContext;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportProjektuebersichtMsServiceTest {

    @Mock
    private ReportProjektuebersichtMsMapper reportMapper;

    @Mock
    private BauprogrammService bauprogrammService;

    @Mock
    private ReportService reportService;

    @Mock
    private ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;

    @Mock
    private StadtbezirkService stadtbezirkService;

    @Mock
    private FoerderbereichService foerderbereichService;

    @Mock
    private UnterabschnittService unterabschnittService;

    @Mock
    private KurzbezeichnungService kurzbezeichnungService;

    @Mock
    private SiedlungsgebietService siedlungsgebietService;

    @InjectMocks
    private ReportProjektuebersichtMsService reportProjektUebersichtMsService;

    @Nested
    class GenerateReportProjektUebersichtMs {

        @Test
        void givenAllParameters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportProjektuebersichtMsDTO parameters = new ReportProjektuebersichtMsDTO(
                    "1999",
                    "1",
                    "1",
                    "1",
                    "1",
                    "KB1",
                    "0",
                    "0",
                    X,
                    "1",
                    "1",
                    ReportFormat.PDF,
                    ReportProjektuebersichtMsSort.PROJEKTNUMMER);

            final Map<String, Object> jasperParameters = new HashMap<>();

            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE4, ReportFormat.PDF, ReportProjektuebersichtMsSort.PROJEKTNUMMER.getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportProjektUebersichtMsService
                    .generateReportProjektuebersichtMs(parameters);

            // Then
            verify(reportMapper, times(1))
                    .toJasperParameters(parameters);

            verify(reportService, times(1)).generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE4,
                    ReportFormat.PDF,
                    ReportProjektuebersichtMsSort.PROJEKTNUMMER.getOrderBy());

            assertThat(generatedReport).isSameAs(expectedReport);
        }
    }

    @Nested
    class GetReportProjektUebersichtMsFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {

            // Stadtbezirksliste
            final List<ListennameStadtbezirkslisteFormContextDTO> allStadtbezirkslisten = List.of(
                    new ListennameStadtbezirkslisteFormContextDTO("SL1", "Test 1"),
                    new ListennameStadtbezirkslisteFormContextDTO("SL2", "Test 2"),
                    new ListennameStadtbezirkslisteFormContextDTO("Sl3", "Test 3"));

            when(listennameStadtbezirkslisteService.getlistennameStadtbezirkslisteFormContextDTOs())
                    .thenReturn(allStadtbezirkslisten);

            // Stadtbezirke
            final List<StadtbezirkFormContextDTO> allStadtbezirke = List.of(
                    new StadtbezirkFormContextDTO("1", "Test 1"),
                    new StadtbezirkFormContextDTO("2", "Test 2"),
                    new StadtbezirkFormContextDTO("3", "Test 3"));

            when(stadtbezirkService.getStadtbezirkFormContextDTOs())
                    .thenReturn(allStadtbezirke);

            // Foerderbereiche
            final List<FoerderbereichFormContextDTO> allFoerderbereiche = List.of(
                    new FoerderbereichFormContextDTO("1", "Test 1"),
                    new FoerderbereichFormContextDTO("2", "Test 2"),
                    new FoerderbereichFormContextDTO("3", "Test 3"));

            when(foerderbereichService.getFoerderbereichFormContextDTOs())
                    .thenReturn(allFoerderbereiche);

            // Unterabschnitte
            final List<UnterabschnittFormContextDTO> allUnterabschnitte = List.of(
                    new UnterabschnittFormContextDTO("1", "Test 1"),
                    new UnterabschnittFormContextDTO("2", "Test 2"),
                    new UnterabschnittFormContextDTO("3", "Test 3"));

            when(unterabschnittService.getUnterabschnittFormContextDTOs())
                    .thenReturn(allUnterabschnitte);

            // Siedlungsgebiete
            final List<SiedlungsgebietFormContextDTO> allSiedlungsgebiete = List.of(
                    new SiedlungsgebietFormContextDTO(1, "Test 1"),
                    new SiedlungsgebietFormContextDTO(2, "Test 2"),
                    new SiedlungsgebietFormContextDTO(3, "Test 3"));

            when(siedlungsgebietService.getSiedlungsgebietFormContextDTOs())
                    .thenReturn(allSiedlungsgebiete);

            // Bauprogramme
            final List<BauprogrammFormContextDTO> allBauprogramme = List.of(
                    new BauprogrammFormContextDTO("1", "Test 1"),
                    new BauprogrammFormContextDTO("2", "Test 2"),
                    new BauprogrammFormContextDTO("3", "Test 3"));

            when(bauprogrammService.getBauprogrammFormContextDTOs())
                    .thenReturn(allBauprogramme);

            // Kurzbezeichnungen
            final List<KurzbezeichnungFormContextDTO> allKurzbezeichnungen = List.of(
                    new KurzbezeichnungFormContextDTO("1", "Test 1"),
                    new KurzbezeichnungFormContextDTO("2", "Test 2"),
                    new KurzbezeichnungFormContextDTO("3", "Test 3"));

            when(kurzbezeichnungService.getKurzbezeichnungFormContextDTOs())
                    .thenReturn(allKurzbezeichnungen);

            // When
            final ReportProjektuebersichtMsFormContext formContext = reportProjektUebersichtMsService
                    .getReportProjektuebersichtMsFormContext();

            // Then

            //Stadtbezirkslisten
            verify(listennameStadtbezirkslisteService, times(1))
                    .getlistennameStadtbezirkslisteFormContextDTOs();

            assertThat(formContext.stadtbezirkslisten()).isEqualTo(allStadtbezirkslisten);

            //Stadtbezirke
            verify(stadtbezirkService, times(1))
                    .getStadtbezirkFormContextDTOs();

            assertThat(formContext.stadtbezirke()).isEqualTo(allStadtbezirke);

            //Foerderbereiche
            verify(foerderbereichService, times(1))
                    .getFoerderbereichFormContextDTOs();

            assertThat(formContext.foerderbereiche()).isEqualTo(allFoerderbereiche);

            //Unterabschnitte
            verify(unterabschnittService, times(1))
                    .getUnterabschnittFormContextDTOs();

            assertThat(formContext.unterabschnitte()).isEqualTo(allUnterabschnitte);

            //Kurzbezeichnungen
            verify(kurzbezeichnungService, times(1))
                    .getKurzbezeichnungFormContextDTOs();

            assertThat(formContext.kurzbezeichnungen()).isEqualTo(allKurzbezeichnungen);

            //Siedlungsgebiete
            verify(siedlungsgebietService, times(1))
                    .getSiedlungsgebietFormContextDTOs();

            assertThat(formContext.siedlungsgebiete()).isEqualTo(allSiedlungsgebiete);

            //Bauprogramme
            verify(bauprogrammService, times(1))
                    .getBauprogrammFormContextDTOs();

            assertThat(formContext.bauprogramme()).isEqualTo(allBauprogramme);
        }
    }
}
