package de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv;

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
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungArchivFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.dto.ReportAuswertungArchivDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.dto.ReportAuswertungArchivMapper;
import de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.dto.ReportAuswertungArchivSort;
import de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.formcontext.ReportAuswertungArchivFormContext;
import de.muenchen.oss.foerdermittel.backend.report.validation.CommonSearchParameters;
import de.muenchen.oss.foerdermittel.backend.report.validation.ReportSearchParameterValidator;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
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
class ReportAuswertungArchivServiceTest {

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
    private ReportAuswertungArchivMapper reportAuswertungarchivMapper;

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
    private ReportAuswertungArchivService reportAuswertungarchivService;

    @Nested
    class GenerateReportAuswertungarchiv {

        private ReportAuswertungArchivDTO createProjektParameters() {
            final ReportAuswertungArchivDTO parameters = mock(ReportAuswertungArchivDTO.class);

            when(parameters.fb()).thenReturn(null);
            when(parameters.bez()).thenReturn(null);
            when(parameters.ua()).thenReturn(null);
            when(parameters.kurz()).thenReturn(null);

            return parameters;
        }

        @Test
        void givenNoFilters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportAuswertungArchivDTO parameters = createProjektParameters();
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(parameters.sort())
                    .thenReturn(ReportAuswertungArchivSort.values()[0]);

            when(reportAuswertungarchivMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_ARCHIV,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportAuswertungarchivService.generateReportAuswertungArchiv(parameters);

            // Then
            verify(reportAuswertungarchivMapper)
                    .toJasperParameters(parameters);

            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_ARCHIV,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy());

            verify(searchParameterValidator).validateCommonSearchParameters(
                    new CommonSearchParameters(
                            null, null, null, null, null, null, null));

            assertThat(generatedReport).isSameAs(expectedReport);
        }

        @Test
        void givenValidParameters_thenShouldDelegateReportGeneration() {
            // Given
            final ReportAuswertungArchivDTO parameters = createProjektParameters();
            when(parameters.sort())
                    .thenReturn(ReportAuswertungArchivSort.values()[0]);
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(reportAuswertungarchivMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_ARCHIV,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport actualReport = reportAuswertungarchivService.generateReportAuswertungArchiv(parameters);

            // Then
            assertThat(actualReport).isSameAs(expectedReport);

            verify(searchParameterValidator).validateCommonSearchParameters(
                    new CommonSearchParameters(
                            null, null, null, null, null, null, null));

            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_ARCHIV,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy());
        }

        @Test
        void givenInvalidSearchParameter_thenShouldPropagateValidationException() {
            // Given
            final ReportAuswertungArchivDTO parameters = createProjektParameters();
            when(parameters.fb()).thenReturn("123");

            final RuntimeException exception = new RuntimeException("Ungültiger Förderbereich");

            doThrow(exception)
                    .when(searchParameterValidator)
                    .validateCommonSearchParameters(
                            new CommonSearchParameters(
                                    "123", null, null, null, null, null, null));

            // When
            final RuntimeException thrown = Assertions.assertThrows(
                    RuntimeException.class,
                    () -> reportAuswertungarchivService
                            .generateReportAuswertungArchiv(parameters));

            // Then
            assertThat(thrown).isSameAs(exception);
            verify(searchParameterValidator)
                    .validateCommonSearchParameters(
                            new CommonSearchParameters(
                                    "123", null, null, null, null, null, null));
            verifyNoInteractions(reportAuswertungarchivMapper, reportService);
        }

        @Test
        void givenEmptyFilters_thenShouldValidateCommonParametersWithoutValues() {
            // Given
            final ReportAuswertungArchivDTO parameters = createProjektParameters();

            when(parameters.sort())
                    .thenReturn(ReportAuswertungArchivSort.values()[0]);

            when(parameters.fb()).thenReturn("");
            when(parameters.bez()).thenReturn("");
            when(parameters.ua()).thenReturn("");
            when(parameters.kurz()).thenReturn("");

            when(reportAuswertungarchivMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportAuswertungarchivService.generateReportAuswertungArchiv(parameters);

            // Then
            verify(searchParameterValidator).validateCommonSearchParameters(
                    new CommonSearchParameters(
                            "", null, "", "", null, "", null));
        }

    }

    @Nested
    class GetReportAuswertungArchivFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<ReportAuswertungArchivFormContextDTO> projektContext = List.of(mock(ReportAuswertungArchivFormContextDTO.class));

            final List<FoerderbereichFormContextDTO> foerderbereichContext = List.of(mock(FoerderbereichFormContextDTO.class));

            final List<StadtbezirkFormContextDTO> stadtbezirkContext = List.of(mock(StadtbezirkFormContextDTO.class));

            final List<UnterabschnittFormContextDTO> unterabschnittContext = List.of(mock(UnterabschnittFormContextDTO.class));

            final List<KurzbezeichnungFormContextDTO> kurzbezeichnungContext = List.of(mock(KurzbezeichnungFormContextDTO.class));

            when(projektService.getReportAuswertungArchivFormContextDTOs())
                    .thenReturn(projektContext);

            when(foerderbereichService.getFoerderbereichFormContextDTOs())
                    .thenReturn(foerderbereichContext);

            when(stadtbezirkService.getStadtbezirkFormContextDTOs())
                    .thenReturn(stadtbezirkContext);

            when(unterabschnittService.getUnterabschnittFormContextDTOs())
                    .thenReturn(unterabschnittContext);

            when(kurzbezeichnungService.getKurzbezeichnungFormContextDTOs())
                    .thenReturn(kurzbezeichnungContext);

            // When
            final ReportAuswertungArchivFormContext formContext = reportAuswertungarchivService.getReportAuswertungArchivFormContext();

            // Then
            verify(projektService).getReportAuswertungArchivFormContextDTOs();
            verify(foerderbereichService).getFoerderbereichFormContextDTOs();
            verify(stadtbezirkService).getStadtbezirkFormContextDTOs();
            verify(unterabschnittService).getUnterabschnittFormContextDTOs();
            verify(kurzbezeichnungService).getKurzbezeichnungFormContextDTOs();

            assertThat(formContext).isNotNull();
        }
    }
}
