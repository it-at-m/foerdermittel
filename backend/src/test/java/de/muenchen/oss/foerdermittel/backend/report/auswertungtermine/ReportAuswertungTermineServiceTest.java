
package de.muenchen.oss.foerdermittel.backend.report.auswertungtermine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto.ReportAuswertungTermineDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto.ReportAuswertungTermineMapper;
import de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto.ReportAuswertungTermineSort;
import de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.formcontext.ReportAuswertungTermineFormContext;
import de.muenchen.oss.foerdermittel.backend.report.validation.CommonSearchParameters;
import de.muenchen.oss.foerdermittel.backend.report.validation.ReportSearchParameterValidator;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportAuswertungTermineServiceTest {

    @Mock
    private StadtbezirkService stadtbezirkService;

    @Mock
    private FoerderbereichService foerderbereichService;

    @Mock
    private ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;

    @Mock
    private ProjektService projektService;

    @Mock
    private ReportService reportService;

    @Mock
    private ReportAuswertungTermineMapper reportAuswertungtermineMapper;

    @Mock
    private ReportSearchParameterValidator searchParameterValidator;

    @Mock
    private KurzbezeichnungService kurzbezeichnungService;

    @Mock
    private BauprogrammService bauprogrammService;

    @Mock
    private UnterabschnittService unterabschnittService;

    @Mock
    private SiedlungsgebietService siedlungsgebietService;

    @InjectMocks
    private ReportAuswertungTermineService reportAuswertungtermineService;

    @Nested
    class GenerateReportAuswertungTermine {

        @Test
        void givenNoFilters_whenGeneratingReport_thenShouldReturnGeneratedReport() {
            // Given
            final ReportAuswertungTermineDTO parameters = createParameters();
            final ReportAuswertungTermineSort sort = ReportAuswertungTermineSort.values()[0];
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(parameters.sort()).thenReturn(sort);
            when(reportAuswertungtermineMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);
            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_TERMINE,
                    ReportFormat.PDF,
                    sort.getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport actualReport = reportAuswertungtermineService.generateReportAuswertungTermine(parameters);

            // Then
            assertThat(actualReport).isSameAs(expectedReport);

            verify(searchParameterValidator).validateCommonSearchParameters(
                    new CommonSearchParameters(
                            null, null, null, null, null, null, null));

            verify(reportAuswertungtermineMapper)
                    .toJasperParameters(parameters);

            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_TERMINE,
                    ReportFormat.PDF,
                    sort.getOrderBy());
        }

        @Test
        void givenEmptyFilters_whenGeneratingReport_thenShouldPreserveEmptyValues() {
            // Given
            final ReportAuswertungTermineDTO parameters = createParameters();
            final ReportAuswertungTermineSort sort = ReportAuswertungTermineSort.values()[0];
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(parameters.sort()).thenReturn(sort);
            when(parameters.fb()).thenReturn("");
            when(parameters.sbl()).thenReturn("");
            when(parameters.bez()).thenReturn("");
            when(parameters.ua()).thenReturn("");
            when(parameters.kurz()).thenReturn("");

            when(reportAuswertungtermineMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);
            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_TERMINE,
                    ReportFormat.PDF,
                    sort.getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport actualReport = reportAuswertungtermineService.generateReportAuswertungTermine(parameters);

            // Then
            assertThat(actualReport).isSameAs(expectedReport);

            verify(searchParameterValidator).validateCommonSearchParameters(
                    new CommonSearchParameters(
                            "", "", "", "", null, "", null));

            verify(reportAuswertungtermineMapper)
                    .toJasperParameters(parameters);

            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_TERMINE,
                    ReportFormat.PDF,
                    sort.getOrderBy());
        }

        @Test
        void givenInvalidSearchParameters_whenGeneratingReport_thenShouldPropagateException() {
            // Given
            final ReportAuswertungTermineDTO parameters = createParameters();
            when(parameters.fb()).thenReturn("123");

            final RuntimeException expectedException = new RuntimeException("Ungültiger Förderbereich");

            doThrow(expectedException)
                    .when(searchParameterValidator)
                    .validateCommonSearchParameters(
                            new CommonSearchParameters(
                                    "123", null, null, null, null, null, null));

            // When
            final RuntimeException actualException = Assertions
                    .assertThrows(
                            RuntimeException.class,
                            () -> reportAuswertungtermineService
                                    .generateReportAuswertungTermine(parameters));

            // Then
            assertThat(actualException).isSameAs(expectedException);

            verify(searchParameterValidator).validateCommonSearchParameters(
                    new CommonSearchParameters(
                            "123", null, null, null, null, null, null));

            verifyNoInteractions(
                    reportAuswertungtermineMapper,
                    reportService);
        }
    }

    @Nested
    class GetReportAuswertungTermineFormContext {

        @Test
        void givenAvailableFormContextData_whenRetrievingFormContext_thenShouldCallAllServices() {
            // Given
            final List<FoerderbereichFormContextDTO> foerderbereichContext = List.of(mock(FoerderbereichFormContextDTO.class));

            final List<ListennameStadtbezirkslisteFormContextDTO> listennameContext = List.of(mock(ListennameStadtbezirkslisteFormContextDTO.class));

            final List<StadtbezirkFormContextDTO> stadtbezirkContext = List.of(mock(StadtbezirkFormContextDTO.class));

            final List<UnterabschnittFormContextDTO> unterabschnittContext = List.of(mock(UnterabschnittFormContextDTO.class));

            final List<KurzbezeichnungFormContextDTO> kurzbezeichnungContext = List.of(mock(KurzbezeichnungFormContextDTO.class));

            when(foerderbereichService.getFoerderbereichFormContextDTOs())
                    .thenReturn(foerderbereichContext);

            when(listennameStadtbezirkslisteService
                    .getlistennameStadtbezirkslisteFormContextDTOs())
                    .thenReturn(listennameContext);

            when(stadtbezirkService.getStadtbezirkFormContextDTOs())
                    .thenReturn(stadtbezirkContext);

            when(unterabschnittService.getUnterabschnittFormContextDTOs())
                    .thenReturn(unterabschnittContext);

            when(kurzbezeichnungService.getKurzbezeichnungFormContextDTOs())
                    .thenReturn(kurzbezeichnungContext);

            // When
            final ReportAuswertungTermineFormContext actualFormContext = reportAuswertungtermineService.getReportAuswertungTermineFormContext();

            // Then
            assertThat(actualFormContext).isNotNull();

            verify(foerderbereichService)
                    .getFoerderbereichFormContextDTOs();

            verify(listennameStadtbezirkslisteService)
                    .getlistennameStadtbezirkslisteFormContextDTOs();

            verify(stadtbezirkService)
                    .getStadtbezirkFormContextDTOs();

            verify(unterabschnittService)
                    .getUnterabschnittFormContextDTOs();

            verify(kurzbezeichnungService)
                    .getKurzbezeichnungFormContextDTOs();
        }
    }

    private ReportAuswertungTermineDTO createParameters() {
        final ReportAuswertungTermineDTO parameters = mock(ReportAuswertungTermineDTO.class);

        when(parameters.fb()).thenReturn(null);
        when(parameters.sbl()).thenReturn(null);
        when(parameters.bez()).thenReturn(null);
        when(parameters.ua()).thenReturn(null);
        when(parameters.kurz()).thenReturn(null);

        return parameters;
    }
}
