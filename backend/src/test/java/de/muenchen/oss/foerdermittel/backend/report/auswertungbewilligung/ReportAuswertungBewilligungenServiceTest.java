package de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungBewilligungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung.dto.ReportAuswertungBewilligungenDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung.dto.ReportAuswertungBewilligungenMapper;
import de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung.dto.ReportAuswertungBewilligungenSort;
import de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung.formcontext.ReportAuswertungBewilligungenFormContext;
import de.muenchen.oss.foerdermittel.backend.report.validation.CommonSearchParameters;
import de.muenchen.oss.foerdermittel.backend.report.validation.ReportSearchParameterValidator;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportAuswertungBewilligungenServiceTest {

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
    private ReportAuswertungBewilligungenMapper reportAuswertungbewilligungenMapper;

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
    private ReportAuswertungBewilligungenService reportAuswertungbewilligungenService;

    @Nested
    class GenerateReportAuswertungbewilligungen {

        private ReportAuswertungBewilligungenDTO createProjektParameters() {
            final ReportAuswertungBewilligungenDTO parameters = mock(ReportAuswertungBewilligungenDTO.class);

            when(parameters.fb()).thenReturn(null);
            when(parameters.sbl()).thenReturn(null);
            when(parameters.bez()).thenReturn(null);
            when(parameters.ua()).thenReturn(null);
            when(parameters.sgt()).thenReturn(null);
            when(parameters.kurz()).thenReturn(null);
            when(parameters.bpg()).thenReturn(null);

            return parameters;
        }

        @Test
        void givenNoFilters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportAuswertungBewilligungenDTO parameters = createProjektParameters();
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(parameters.type()).thenReturn(ReportFormat.PDF);
            when(parameters.sort())
                    .thenReturn(ReportAuswertungBewilligungenSort.values()[0]);

            when(reportAuswertungbewilligungenMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_BEWILL1,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportAuswertungbewilligungenService.generateReportAuswertungBewilligungen(parameters);

            // Then
            verify(reportAuswertungbewilligungenMapper)
                    .toJasperParameters(parameters);

            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_BEWILL1,
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
            final ReportAuswertungBewilligungenDTO parameters = createProjektParameters();
            when(parameters.type()).thenReturn(ReportFormat.PDF);
            when(parameters.sort())
                    .thenReturn(ReportAuswertungBewilligungenSort.values()[0]);
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(reportAuswertungbewilligungenMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_BEWILL1,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport actualReport = reportAuswertungbewilligungenService.generateReportAuswertungBewilligungen(parameters);

            // Then
            assertThat(actualReport).isSameAs(expectedReport);

            verify(searchParameterValidator).validateCommonSearchParameters(
                    new CommonSearchParameters(
                            null, null, null, null, null, null, null));

            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_BEWILL1,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy());
        }

        @Test
        void givenInvalidSearchParameter_thenShouldPropagateValidationException() {
            // Given
            final ReportAuswertungBewilligungenDTO parameters = createProjektParameters();
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
                    () -> reportAuswertungbewilligungenService
                            .generateReportAuswertungBewilligungen(parameters));

            // Then
            assertThat(thrown).isSameAs(exception);
            verify(searchParameterValidator)
                    .validateCommonSearchParameters(
                            new CommonSearchParameters(
                                    "123", null, null, null, null, null, null));
            verifyNoInteractions(reportAuswertungbewilligungenMapper, reportService);
        }

        @Test
        void givenEmptyFilters_thenShouldValidateCommonParametersWithoutValues() {
            // Given
            final ReportAuswertungBewilligungenDTO parameters = createProjektParameters();

            when(parameters.type()).thenReturn(ReportFormat.PDF);
            when(parameters.sort())
                    .thenReturn(ReportAuswertungBewilligungenSort.values()[0]);

            when(parameters.fb()).thenReturn("");
            when(parameters.sbl()).thenReturn("");
            when(parameters.bez()).thenReturn("");
            when(parameters.ua()).thenReturn("");
            when(parameters.sgt()).thenReturn("");
            when(parameters.kurz()).thenReturn("");
            when(parameters.bpg()).thenReturn("");

            when(reportAuswertungbewilligungenMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportAuswertungbewilligungenService.generateReportAuswertungBewilligungen(parameters);

            // Then
            verify(searchParameterValidator).validateCommonSearchParameters(
                    new CommonSearchParameters(
                            "", "", "", "", "", "", ""));
        }

    }

    @Nested
    class GetReportAuswertungBewilligungFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<ReportAuswertungBewilligungFormContextDTO> projektContext = List.of(mock(ReportAuswertungBewilligungFormContextDTO.class));

            final List<FoerderbereichFormContextDTO> foerderbereichContext = List.of(mock(FoerderbereichFormContextDTO.class));

            final List<ListennameStadtbezirkslisteFormContextDTO> listennameContext = List.of(mock(ListennameStadtbezirkslisteFormContextDTO.class));

            final List<StadtbezirkFormContextDTO> stadtbezirkContext = List.of(mock(StadtbezirkFormContextDTO.class));

            final List<UnterabschnittFormContextDTO> unterabschnittContext = List.of(mock(UnterabschnittFormContextDTO.class));

            final List<KurzbezeichnungFormContextDTO> kurzbezeichnungContext = List.of(mock(KurzbezeichnungFormContextDTO.class));

            final List<BauprogrammFormContextDTO> bauprogrammContext = List.of(mock(BauprogrammFormContextDTO.class));

            final List<SiedlungsgebietFormContextDTO> siedlungsgebietContext = List.of(mock(SiedlungsgebietFormContextDTO.class));

            when(projektService.getReportAuswertungBewilligungFormContextDTOs())
                    .thenReturn(projektContext);

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

            when(bauprogrammService.getBauprogrammFormContextDTOs())
                    .thenReturn(bauprogrammContext);

            when(siedlungsgebietService.getSiedlungsgebietFormContextDTOs())
                    .thenReturn(siedlungsgebietContext);

            // When
            final ReportAuswertungBewilligungenFormContext formContext = reportAuswertungbewilligungenService.getReportAuswertungBewilligungenFormContext();

            // Then
            verify(projektService).getReportAuswertungBewilligungFormContextDTOs();
            verify(foerderbereichService).getFoerderbereichFormContextDTOs();

            verify(listennameStadtbezirkslisteService)
                    .getlistennameStadtbezirkslisteFormContextDTOs();

            verify(stadtbezirkService).getStadtbezirkFormContextDTOs();
            verify(unterabschnittService).getUnterabschnittFormContextDTOs();
            verify(kurzbezeichnungService).getKurzbezeichnungFormContextDTOs();
            verify(bauprogrammService).getBauprogrammFormContextDTOs();
            verify(siedlungsgebietService).getSiedlungsgebietFormContextDTOs();

            assertThat(formContext).isNotNull();
            assertThat(formContext.projs()).isEqualTo(projektContext);
            assertThat(formContext.fbs()).isEqualTo(foerderbereichContext);
            assertThat(formContext.sbls()).isEqualTo(listennameContext);
            assertThat(formContext.bezs()).isEqualTo(stadtbezirkContext);
            assertThat(formContext.uas()).isEqualTo(unterabschnittContext);
            assertThat(formContext.kurzs()).isEqualTo(kurzbezeichnungContext);
            assertThat(formContext.bpgs()).isEqualTo(bauprogrammContext);
            assertThat(formContext.sgts()).isEqualTo(siedlungsgebietContext);
        }
    }
}
