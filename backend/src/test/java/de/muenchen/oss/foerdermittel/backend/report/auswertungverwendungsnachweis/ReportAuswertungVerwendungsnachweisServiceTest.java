
package de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis.dto.ReportAuswertungVerwendungsnachweisDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis.dto.ReportAuswertungVerwendungsnachweisMapper;
import de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis.dto.ReportAuswertungVerwendungsnachweisSort;
import de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis.formcontext.ReportAuswertungVerwendungsnachweisFormContext;
import de.muenchen.oss.foerdermittel.backend.report.validation.CommonSearchParameters;
import de.muenchen.oss.foerdermittel.backend.report.validation.ReportSearchParameterValidator;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
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
class ReportAuswertungVerwendungsnachweisServiceTest {

    @Mock
    private FoerderbereichService foerderbereichService;

    @Mock
    private ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;

    @Mock
    private StadtbezirkService stadtbezirkService;

    @Mock
    private KurzbezeichnungService kurzbezeichnungService;

    @Mock
    private BauprogrammService bauprogrammService;

    @Mock
    private UnterabschnittService unterabschnittService;

    @Mock
    private SiedlungsgebietService siedlungsgebietService;

    @Mock
    private KrankenhausService krankenhausService;

    @Mock
    private ReportAuswertungVerwendungsnachweisMapper reportAuswertungVerwendungsnachweisMapper;

    @Mock
    private ReportService reportService;

    @Mock
    private ProjektService projektService;

    @Mock
    private ReportSearchParameterValidator reportSearchParameterValidator;

    @InjectMocks
    private ReportAuswertungVerwendungsnachweisService reportAuswertungVerwendungsnachweisService;

    @Nested
    class GenerateReportAuswertungVerwendungsnachweis {

        @Test
        void givenValidParameters_thenGeneratesReport() {
            // Given
            final ReportAuswertungVerwendungsnachweisDTO parameters = new ReportAuswertungVerwendungsnachweisDTO(
                    "2025",
                    "01",
                    "SBL1",
                    "1",
                    "11",
                    "UA1",
                    "KRH1",
                    "Projekt 1",
                    "Projektstraße 1",
                    "KB1",
                    "0",
                    "0",
                    "0",
                    "0",
                    Krisofp.K,
                    "1",
                    "1",
                    ReportFormat.PDF,
                    ReportAuswertungVerwendungsnachweisSort.PROJEKTNUMMER);

            final Map<String, Object> jasperParameters = new HashMap<>();

            final GeneratedReport expectedReport = new GeneratedReport(
                    "test.pdf",
                    ReportFormat.PDF.getContentType(),
                    outputStream -> {
                    });

            final CommonSearchParameters commonSearchParameters = new CommonSearchParameters(
                    parameters.fb(),
                    parameters.sbl(),
                    parameters.bez(),
                    parameters.ua(),
                    parameters.sgt(),
                    parameters.kurz(),
                    parameters.bpg());

            when(reportAuswertungVerwendungsnachweisMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_VN,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportAuswertungVerwendungsnachweisService.generateReportAuswertungVerwendungsnachweis(parameters);

            // Then
            assertThat(generatedReport).isSameAs(expectedReport);

            verify(reportSearchParameterValidator, times(1))
                    .validateCommonSearchParameters(commonSearchParameters);

            verify(krankenhausService, times(1))
                    .checkExistsByKrankenhaus(parameters.krh());

            verify(reportAuswertungVerwendungsnachweisMapper, times(1))
                    .toJasperParameters(parameters);

            verify(reportService, times(1)).generateReport(
                    jasperParameters,
                    ReportType.FMW_VN,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy());
        }

        @Test
        void givenInvalidCommonSearchParameters_thenDoesNotGenerateReport() {
            // Given
            final ReportAuswertungVerwendungsnachweisDTO parameters = new ReportAuswertungVerwendungsnachweisDTO(
                    "2025",
                    "01",
                    "SBL1",
                    "1",
                    "11",
                    "UA1",
                    "",
                    "Projekt 1",
                    "Projektstraße 1",
                    "KB1",
                    "0",
                    "0",
                    "0",
                    "0",
                    Krisofp.K,
                    "1",
                    "1",
                    ReportFormat.PDF,
                    ReportAuswertungVerwendungsnachweisSort.PROJEKTNUMMER);

            doThrow(new IllegalArgumentException("Invalid search parameters"))
                    .when(reportSearchParameterValidator)
                    .validateCommonSearchParameters(any(CommonSearchParameters.class));

            // When / Then
            assertThrows(
                    IllegalArgumentException.class,
                    () -> reportAuswertungVerwendungsnachweisService.generateReportAuswertungVerwendungsnachweis(parameters));

            verify(reportSearchParameterValidator, times(1))
                    .validateCommonSearchParameters(any(CommonSearchParameters.class));

            verifyNoInteractions(
                    reportAuswertungVerwendungsnachweisMapper,
                    reportService,
                    krankenhausService);
        }
    }

    @Nested
    class GetReportAuswertungVerwendungsnachweisFormContext {

        @Test
        void givenFormContext_thenReturnsAllFormContextData() {
            // Given
            when(projektService.getReportAuswertungVerwendungsnachweisFormContextDTOs()).thenReturn(List.of());
            when(foerderbereichService.getFoerderbereichFormContextDTOs()).thenReturn(List.of());
            when(listennameStadtbezirkslisteService.getlistennameStadtbezirkslisteFormContextDTOs())
                    .thenReturn(List.of());
            when(stadtbezirkService.getStadtbezirkFormContextDTOs()).thenReturn(List.of());
            when(unterabschnittService.getUnterabschnittFormContextDTOs()).thenReturn(List.of());
            when(kurzbezeichnungService.getKurzbezeichnungFormContextDTOs()).thenReturn(List.of());
            when(bauprogrammService.getBauprogrammFormContextDTOs()).thenReturn(List.of());
            when(siedlungsgebietService.getSiedlungsgebietFormContextDTOs()).thenReturn(List.of());
            when(krankenhausService.getKrankenhausFormContextDTOs()).thenReturn(List.of());

            // When
            final ReportAuswertungVerwendungsnachweisFormContext formContext = reportAuswertungVerwendungsnachweisService
                    .getReportAuswertungVerwendungsnachweisFormContext();

            // Then
            assertThat(formContext).isNotNull();

            verify(projektService, times(1)).getReportAuswertungVerwendungsnachweisFormContextDTOs();
            verify(foerderbereichService, times(1)).getFoerderbereichFormContextDTOs();
            verify(listennameStadtbezirkslisteService, times(1))
                    .getlistennameStadtbezirkslisteFormContextDTOs();
            verify(stadtbezirkService, times(1)).getStadtbezirkFormContextDTOs();
            verify(unterabschnittService, times(1)).getUnterabschnittFormContextDTOs();
            verify(kurzbezeichnungService, times(1)).getKurzbezeichnungFormContextDTOs();
            verify(bauprogrammService, times(1)).getBauprogrammFormContextDTOs();
            verify(siedlungsgebietService, times(1)).getSiedlungsgebietFormContextDTOs();
            verify(krankenhausService, times(1)).getKrankenhausFormContextDTOs();
        }
    }
}
