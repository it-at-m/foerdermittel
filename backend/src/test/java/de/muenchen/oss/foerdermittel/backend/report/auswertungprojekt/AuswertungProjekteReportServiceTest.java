package de.muenchen.oss.foerdermittel.backend.report.auswertungprojekt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.auswertungprojekt.dto.AuswertungProjekteReportMapper;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportAuswertungProjekteDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportAuswertungProjekteSort;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportAuswertungProjektFormContext;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import java.math.BigDecimal;
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
class AuswertungProjekteReportServiceTest {

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
    private AuswertungProjekteReportMapper auswertungProjekteReportMapper;

    @Mock
    private KurzbezeichnungService kurzbezeichnungService;

    @Mock
    private BauprogrammService bauprogrammService;

    @Mock
    private UnterabschnittService unterabschnittService;

    @Mock
    private SiedlungsgebietService siedlungsgebietService;

    @InjectMocks
    private AuswertungProjekteReportService auswertungProjekteReportService;

    @Nested
    class GenerateReportAuswertungProjekte {

        private ReportAuswertungProjekteDTO createProjektParameters() {
            final ReportAuswertungProjekteDTO parameters = mock(ReportAuswertungProjekteDTO.class);

            when(parameters.type()).thenReturn(ReportFormat.PDF);
            when(parameters.fb()).thenReturn(null);
            when(parameters.sbl()).thenReturn(null);
            when(parameters.bez()).thenReturn(null);
            when(parameters.ua()).thenReturn(null);
            when(parameters.sgt()).thenReturn(null);
            when(parameters.kurz()).thenReturn(null);
            when(parameters.bpg()).thenReturn(null);
            when(parameters.sort())
                    .thenReturn(ReportAuswertungProjekteSort.values()[0]);

            return parameters;
        }

        @Test
        void givenNoFilters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportAuswertungProjekteDTO parameters = createProjektParameters();
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(auswertungProjekteReportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = auswertungProjekteReportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(auswertungProjekteReportMapper)
                    .toJasperParameters(parameters);

            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy());

            verifyNoInteractions(
                    foerderbereichService,
                    listennameStadtbezirkslisteService,
                    stadtbezirkService,
                    unterabschnittService,
                    siedlungsgebietService,
                    kurzbezeichnungService,
                    bauprogrammService);

            assertThat(generatedReport).isSameAs(expectedReport);
        }

        @Test
        void givenValidParameters_thenShouldDelegateReportGeneration() {
            // Given
            final ReportAuswertungProjekteDTO parameters = createProjektParameters();
            final Map<String, Object> jasperParameters = new HashMap<>();
            final GeneratedReport expectedReport = mock(GeneratedReport.class);

            when(auswertungProjekteReportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport actualReport = auswertungProjekteReportService.generateReportAuswertungProjekt(parameters);

            // Then
            assertThat(actualReport).isSameAs(expectedReport);

            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_PROJEKTE,
                    ReportFormat.PDF,
                    parameters.sort().getOrderBy());
        }

        @Test
        void givenFoerderbereich_thenShouldCheckFoerderbereich() {
            // Given
            final ReportAuswertungProjekteDTO parameters = createProjektParameters();
            when(parameters.fb()).thenReturn("123");

            when(auswertungProjekteReportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            auswertungProjekteReportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(foerderbereichService)
                    .checkExistsByFoerderbereich(new BigDecimal("123"));
        }

        @Test
        void givenListennameStadtbezirksliste_thenShouldCheckListenname() {
            // Given
            final ReportAuswertungProjekteDTO parameters = createProjektParameters();
            when(parameters.sbl()).thenReturn("LISTE");

            when(auswertungProjekteReportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            auswertungProjekteReportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(listennameStadtbezirkslisteService)
                    .checkExistsByListenname("LISTE");
        }

        @Test
        void givenStadtbezirk_thenShouldCheckStadtbezirk() {
            // Given
            final ReportAuswertungProjekteDTO parameters = createProjektParameters();
            when(parameters.bez()).thenReturn("12");

            when(auswertungProjekteReportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            auswertungProjekteReportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(stadtbezirkService)
                    .checkExistsByStadtbezirk(new BigDecimal("12"));
        }

        @Test
        void givenUnterabschnitt_thenShouldCheckUnterabschnitt() {
            // Given
            final ReportAuswertungProjekteDTO parameters = createProjektParameters();
            when(parameters.ua()).thenReturn("UA01");

            when(auswertungProjekteReportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            auswertungProjekteReportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(unterabschnittService)
                    .checkExistsByUnterabschnitt("UA01");
        }

        @Test
        void givenSiedlungsgebiet_thenShouldCheckSiedlungsgebiet() {
            // Given
            final ReportAuswertungProjekteDTO parameters = createProjektParameters();
            when(parameters.sgt()).thenReturn("15");

            when(auswertungProjekteReportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            auswertungProjekteReportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(siedlungsgebietService)
                    .checkExistsBySiedlungsgebiet(new BigDecimal("15"));
        }

        @Test
        void givenKurzbezeichnung_thenShouldCheckKurzbezeichnung() {
            // Given
            final ReportAuswertungProjekteDTO parameters = createProjektParameters();
            when(parameters.kurz()).thenReturn("KURZ");

            when(auswertungProjekteReportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            auswertungProjekteReportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(kurzbezeichnungService)
                    .checkExistsByKurzbezeichnung("KURZ");
        }

        @Test
        void givenBauprogramm_thenShouldCheckBauprogramm() {
            // Given
            final ReportAuswertungProjekteDTO parameters = createProjektParameters();
            when(parameters.bpg()).thenReturn("25");

            when(auswertungProjekteReportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            auswertungProjekteReportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(bauprogrammService)
                    .checkExistsByBauprogramm(new BigDecimal("25"));
        }

        @Test
        void givenEmptyFilters_thenShouldNotCheckAnyFilterService() {
            // Given
            final ReportAuswertungProjekteDTO parameters = createProjektParameters();

            when(parameters.fb()).thenReturn("");
            when(parameters.sbl()).thenReturn("");
            when(parameters.bez()).thenReturn("");
            when(parameters.ua()).thenReturn("");
            when(parameters.sgt()).thenReturn("");
            when(parameters.kurz()).thenReturn("");
            when(parameters.bpg()).thenReturn("");

            when(auswertungProjekteReportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            auswertungProjekteReportService.generateReportAuswertungProjekt(parameters);

            // Then
            verifyNoInteractions(
                    foerderbereichService,
                    listennameStadtbezirkslisteService,
                    stadtbezirkService,
                    unterabschnittService,
                    siedlungsgebietService,
                    kurzbezeichnungService,
                    bauprogrammService);
        }

        @Test
        void givenFoerderbereichNotFound_thenShouldPropagateException() {
            // Given
            final ReportAuswertungProjekteDTO parameters = mock(ReportAuswertungProjekteDTO.class);

            when(parameters.fb()).thenReturn("123");

            final NotFoundException exception = new NotFoundException(Foerderbereich.class, "123");

            doThrow(exception)
                    .when(foerderbereichService)
                    .checkExistsByFoerderbereich(new BigDecimal("123"));

            // When
            final Exception thrown = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> auswertungProjekteReportService
                            .generateReportAuswertungProjekt(parameters));

            // Then
            assertThat(thrown).isSameAs(exception);

            verify(foerderbereichService)
                    .checkExistsByFoerderbereich(new BigDecimal("123"));

            verifyNoInteractions(auswertungProjekteReportMapper);
        }
    }

    @Nested
    class GetReportAuswertungProjektFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<ReportAuswertungProjektFormContextDTO> projektContext = List.of(mock(ReportAuswertungProjektFormContextDTO.class));

            final List<FoerderbereichFormContextDTO> foerderbereichContext = List.of(mock(FoerderbereichFormContextDTO.class));

            final List<ListennameStadtbezirkslisteFormContextDTO> listennameContext = List.of(mock(ListennameStadtbezirkslisteFormContextDTO.class));

            final List<StadtbezirkFormContextDTO> stadtbezirkContext = List.of(mock(StadtbezirkFormContextDTO.class));

            final List<UnterabschnittFormContextDTO> unterabschnittContext = List.of(mock(UnterabschnittFormContextDTO.class));

            final List<KurzbezeichnungFormContextDTO> kurzbezeichnungContext = List.of(mock(KurzbezeichnungFormContextDTO.class));

            final List<BauprogrammFormContextDTO> bauprogrammContext = List.of(mock(BauprogrammFormContextDTO.class));

            final List<SiedlungsgebietFormContextDTO> siedlungsgebietContext = List.of(mock(SiedlungsgebietFormContextDTO.class));

            when(projektService.getReportAuswertungProjektFormContextDTOs())
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
            final ReportAuswertungProjektFormContext formContext = auswertungProjekteReportService.getReportAuswertungProjekt();

            // Then
            verify(projektService).getReportAuswertungProjektFormContextDTOs();
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
