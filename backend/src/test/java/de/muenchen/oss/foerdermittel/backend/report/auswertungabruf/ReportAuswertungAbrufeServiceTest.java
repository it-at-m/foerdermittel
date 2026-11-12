package de.muenchen.oss.foerdermittel.backend.report.auswertungabruf;

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
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungAntragFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.referat.ReferatService;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.ReportAuswertungAbrufeService;
import de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto.ReportAuswertungAbrufeDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto.ReportAuswertungAbrufeMapper;
import de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto.ReportAuswertungAbrufeSort;
import de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.formcontext.ReportAuswertungAbrufeFormContext;
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
class ReportAuswertungAbrufeServiceTest {

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
    private ReportAuswertungAbrufeMapper reportAuswertungabrufeMapper;

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

    @Mock
    private ReferatService referatService;

    @Mock
    private KrankenhausService krankenhausService;

    @InjectMocks
    private ReportAuswertungAbrufeService reportAuswertungabrufeService;

    @Nested
    class GenerateReportAuswertungabrufe {

        private ReportAuswertungAbrufeDTO createProjektParameters() {
            final ReportAuswertungAbrufeDTO parameters = mock(ReportAuswertungAbrufeDTO.class);

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
            final ReportAuswertungAbrufeDTO parameters = createProjektParameters();
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(parameters.type()).thenReturn(ReportFormat.PDF);
            when(parameters.sort())
                    .thenReturn(ReportAuswertungAbrufeSort.values()[0]);

            when(reportAuswertungabrufeMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_ABRUFE1,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportAuswertungabrufeService.generateReportAuswertungAbrufe(parameters);

            // Then
            verify(reportAuswertungabrufeMapper)
                    .toJasperParameters(parameters);

            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_ABRUFE1,
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
            final ReportAuswertungAbrufeDTO parameters = createProjektParameters();
            when(parameters.type()).thenReturn(ReportFormat.PDF);
            when(parameters.sort())
                    .thenReturn(ReportAuswertungAbrufeSort.values()[0]);
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(reportAuswertungabrufeMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_ABRUFE1,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport actualReport = reportAuswertungabrufeService.generateReportAuswertungAbrufe(parameters);

            // Then
            assertThat(actualReport).isSameAs(expectedReport);

            verify(searchParameterValidator).validateCommonSearchParameters(
                    new CommonSearchParameters(
                            null, null, null, null, null, null, null));

            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_ABRUFE1,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy());
        }

        @Test
        void givenInvalidSearchParameter_thenShouldPropagateValidationException() {
            // Given
            final ReportAuswertungAbrufeDTO parameters = createProjektParameters();
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
                    () -> reportAuswertungabrufeService
                            .generateReportAuswertungAbrufe(parameters));

            // Then
            assertThat(thrown).isSameAs(exception);
            verify(searchParameterValidator)
                    .validateCommonSearchParameters(
                            new CommonSearchParameters(
                                    "123", null, null, null, null, null, null));
            verifyNoInteractions(reportAuswertungabrufeMapper, reportService);
        }

        @Test
        void givenEmptyFilters_thenShouldValidateCommonParametersWithoutValues() {
            // Given
            final ReportAuswertungAbrufeDTO parameters = createProjektParameters();

            when(parameters.type()).thenReturn(ReportFormat.PDF);
            when(parameters.sort())
                    .thenReturn(ReportAuswertungAbrufeSort.values()[0]);

            when(parameters.fb()).thenReturn("");
            when(parameters.sbl()).thenReturn("");
            when(parameters.bez()).thenReturn("");
            when(parameters.ua()).thenReturn("");
            when(parameters.sgt()).thenReturn("");
            when(parameters.kurz()).thenReturn("");
            when(parameters.bpg()).thenReturn("");

            when(reportAuswertungabrufeMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportAuswertungabrufeService.generateReportAuswertungAbrufe(parameters);

            // Then
            verify(searchParameterValidator).validateCommonSearchParameters(
                    new CommonSearchParameters(
                            "", "", "", "", "", "", ""));
        }

    }

    @Nested
    class GetReportAuswertungAntragFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<ReportAuswertungAntragFormContextDTO> projektContext = List.of(mock(ReportAuswertungAntragFormContextDTO.class));

            final List<FoerderbereichFormContextDTO> foerderbereichContext = List.of(mock(FoerderbereichFormContextDTO.class));

            final List<ListennameStadtbezirkslisteFormContextDTO> listennameContext = List.of(mock(ListennameStadtbezirkslisteFormContextDTO.class));

            final List<StadtbezirkFormContextDTO> stadtbezirkContext = List.of(mock(StadtbezirkFormContextDTO.class));

            final List<UnterabschnittFormContextDTO> unterabschnittContext = List.of(mock(UnterabschnittFormContextDTO.class));

            final List<KurzbezeichnungFormContextDTO> kurzbezeichnungContext = List.of(mock(KurzbezeichnungFormContextDTO.class));

            final List<BauprogrammFormContextDTO> bauprogrammContext = List.of(mock(BauprogrammFormContextDTO.class));

            final List<SiedlungsgebietFormContextDTO> siedlungsgebietContext = List.of(mock(SiedlungsgebietFormContextDTO.class));

            final List<ReferatFormContextDTO> referatContext = List.of(mock(ReferatFormContextDTO.class));

            final List<KrankenhausFormContextDTO> krankenhausContext = List.of(mock(KrankenhausFormContextDTO.class));

            when(projektService.getReportAuswertungAntragFormContextDTOs())
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

            when(referatService.getReferatFormContextDTOs())
                    .thenReturn(referatContext);

            when(krankenhausService.getKrankenhausFormContextDTOs())
                    .thenReturn(krankenhausContext);

            // When
            final ReportAuswertungAbrufeFormContext formContext = reportAuswertungabrufeService.getReportAuswertungAbrufeFormContext();

            // Then
            verify(projektService).getReportAuswertungAntragFormContextDTOs();
            verify(foerderbereichService).getFoerderbereichFormContextDTOs();

            verify(listennameStadtbezirkslisteService)
                    .getlistennameStadtbezirkslisteFormContextDTOs();

            verify(stadtbezirkService).getStadtbezirkFormContextDTOs();
            verify(unterabschnittService).getUnterabschnittFormContextDTOs();
            verify(kurzbezeichnungService).getKurzbezeichnungFormContextDTOs();
            verify(bauprogrammService).getBauprogrammFormContextDTOs();
            verify(siedlungsgebietService).getSiedlungsgebietFormContextDTOs();

            assertThat(formContext).isNotNull();
        }
    }
}
