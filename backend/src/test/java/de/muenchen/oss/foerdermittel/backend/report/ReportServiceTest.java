package de.muenchen.oss.foerdermittel.backend.report;

import static de.muenchen.oss.foerdermittel.backend.report.ReportService.SORT_PARAMETER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportAuwertungProjektSort;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportAuswertungProjekteDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportMapper;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichworteDTO;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportAuswertungProjektFormContext;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportStichworteFormContext;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.Stichwortbereich;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.StichwortbereichService;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private StichwortbereichService stichwortbereichService;

    @Mock
    private ProjektService projektService;

    @Mock
    private JasperReportService jasperReportService;

    @Mock
    private ReportMapper reportMapper;

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

    @InjectMocks
    private ReportService reportService;

    @Nested
    class GenerateReportStichworte {

        @Test
        void givenNoWriteInteraction_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final String bereich = "TEST";

            final ReportStichworteDTO parameters =
                    new ReportStichworteDTO(bereich);

            final Map<String, Object> jasperParameters =
                    new HashMap<>();

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            // When
            final GeneratedReport generatedReport =
                    reportService.generateReportStichworte(parameters);

            // Then
            verify(stichwortbereichService, times(1))
                    .checkExistsByBereich(bereich);

            verify(reportMapper, times(1))
                    .toJasperParameters(parameters);

            verifyNoInteractions(jasperReportService);

            assertThat(generatedReport).isNotNull();

            assertThat(generatedReport.contentType())
                    .isEqualTo(ReportFormat.PDF.getContentType());

            assertThat(generatedReport.fileName())
                    .startsWith(ReportType.FMW_ABLAGEINDEX.getFileName())
                    .endsWith(ReportFormat.PDF.getFileExtension());

            assertThat(jasperParameters)
                    .containsEntry(
                            SORT_PARAMETER,
                            "ORDER BY stb_bereich ASC, nr ASC, wort ASC");
        }

        @Test
        void givenWriteInteraction_thenShouldCallJasperServiceCorrectly()
                throws JRException, SQLException, IOException {

            // Given
            final String bereich = "TEST";

            final ReportStichworteDTO parameters =
                    new ReportStichworteDTO(bereich);

            final Map<String, Object> jasperParameters =
                    new HashMap<>();

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            final OutputStream outputStream =
                    new ByteArrayOutputStream();

            // When
            final GeneratedReport generatedReport =
                    reportService.generateReportStichworte(parameters);

            generatedReport.writer().write(outputStream);

            // Then
            verify(jasperReportService, times(1))
                    .generateReportWithParameters(
                            ReportType.FMW_ABLAGEINDEX,
                            ReportFormat.PDF,
                            jasperParameters,
                            outputStream);
        }

        @Test
        void givenNotFound_thenShouldThrowNotFoundException() {
            // Given
            final String bereich = "test";

            final ReportStichworteDTO parameters =
                    new ReportStichworteDTO(bereich);

            doThrow(new NotFoundException(
                    Stichwortbereich.class,
                    bereich))
                    .when(stichwortbereichService)
                    .checkExistsByBereich(bereich);

            // When
            final Exception exception =
                    Assertions.assertThrows(
                            NotFoundException.class,
                            () -> reportService.generateReportStichworte(parameters));

            // Then
            verify(stichwortbereichService, times(1))
                    .checkExistsByBereich(bereich);

            assertThat(exception.getMessage())
                    .isEqualTo(
                            String.format(
                                    "The %s with ID %s was not found.",
                                    Stichwortbereich.class.getSimpleName(),
                                    bereich));
        }
    }

    @Nested
    class GetReportStichworteFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<StichwortbereichFormContextDTO> allBereiche =
                    List.of(
                            new StichwortbereichFormContextDTO("K", "Test"),
                            new StichwortbereichFormContextDTO("L", "Test 2"),
                            new StichwortbereichFormContextDTO("M", "Test 3"));

            when(stichwortbereichService
                    .getStichwortbereichFormContextDTOs())
                    .thenReturn(allBereiche);

            // When
            final ReportStichworteFormContext formContext =
                    reportService.getReportStichworte();

            // Then
            verify(stichwortbereichService, times(1))
                    .getStichwortbereichFormContextDTOs();

            assertThat(formContext.bereiche())
                    .isEqualTo(allBereiche);
        }
    }

    @Nested
    class GenerateReportAuswertungProjekte {

        /**
         * Creates a mocked DTO with all optional filters empty and
         * a valid default sort value.
         */
        private ReportAuswertungProjekteDTO createProjektParameters() {
            final ReportAuswertungProjekteDTO parameters =
                    mock(ReportAuswertungProjekteDTO.class);

            when(parameters.fb()).thenReturn(null);
            when(parameters.sbl()).thenReturn(null);
            when(parameters.bez()).thenReturn(null);
            when(parameters.ua()).thenReturn(null);
            when(parameters.sgt()).thenReturn(null);
            when(parameters.kurz()).thenReturn(null);
            when(parameters.bpg()).thenReturn(null);

            when(parameters.sort())
                    .thenReturn(ReportAuwertungProjektSort.values()[0]);

            return parameters;
        }

        @Test
        void givenNoFilters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportAuswertungProjekteDTO parameters =
                    createProjektParameters();

            final Map<String, Object> jasperParameters =
                    new HashMap<>();

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            // When
            final GeneratedReport generatedReport =
                    reportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(reportMapper, times(1))
                    .toJasperParameters(parameters);

            verifyNoInteractions(
                    foerderbereichService,
                    listennameStadtbezirkslisteService,
                    stadtbezirkService,
                    unterabschnittService,
                    siedlungsgebietService,
                    kurzbezeichnungService,
                    bauprogrammService,
                    jasperReportService);

            assertThat(generatedReport).isNotNull();

            assertThat(generatedReport.contentType())
                    .isEqualTo(ReportFormat.PDF.getContentType());

            assertThat(generatedReport.fileName())
                    .startsWith(ReportType.FMW_PROJEKTE.getFileName())
                    .endsWith(ReportFormat.PDF.getFileExtension());

            assertThat(jasperParameters)
                    .containsEntry(
                            SORT_PARAMETER,
                            ReportAuwertungProjektSort.values()[0].getOrderBy());
        }

        @Test
        void givenWriteInteraction_thenShouldCallJasperServiceCorrectly()
                throws JRException, SQLException, IOException {

            // Given
            final ReportAuswertungProjekteDTO parameters =
                    createProjektParameters();

            final Map<String, Object> jasperParameters =
                    new HashMap<>();

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            final OutputStream outputStream =
                    new ByteArrayOutputStream();

            // When
            final GeneratedReport generatedReport =
                    reportService.generateReportAuswertungProjekt(parameters);

            generatedReport.writer().write(outputStream);

            // Then
            verify(jasperReportService, times(1))
                    .generateReportWithParameters(
                            ReportType.FMW_PROJEKTE,
                            ReportFormat.PDF,
                            jasperParameters,
                            outputStream);
        }

        @Test
        void givenFoerderbereich_thenShouldCheckFoerderbereich() {
            // Given
            final ReportAuswertungProjekteDTO parameters =
                    createProjektParameters();

            when(parameters.fb()).thenReturn("123");

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(foerderbereichService)
                    .checkExistsByFoerderbereich(
                            new BigDecimal("123"));
        }

        @Test
        void givenListennameStadtbezirksliste_thenShouldCheckListenname() {
            // Given
            final ReportAuswertungProjekteDTO parameters =
                    createProjektParameters();

            when(parameters.sbl()).thenReturn("LISTE");

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(listennameStadtbezirkslisteService)
                    .checkExistsByListenname("LISTE");
        }

        @Test
        void givenStadtbezirk_thenShouldCheckStadtbezirk() {
            // Given
            final ReportAuswertungProjekteDTO parameters =
                    createProjektParameters();

            when(parameters.bez()).thenReturn("12");

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(stadtbezirkService)
                    .checkExistsByStadtbezirk(
                            new BigDecimal("12"));
        }

        @Test
        void givenUnterabschnitt_thenShouldCheckUnterabschnitt() {
            // Given
            final ReportAuswertungProjekteDTO parameters =
                    createProjektParameters();

            when(parameters.ua()).thenReturn("UA01");

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(unterabschnittService)
                    .checkExistsByUnterabschnitt("UA01");
        }

        @Test
        void givenSiedlungsgebiet_thenShouldCheckSiedlungsgebiet() {
            // Given
            final ReportAuswertungProjekteDTO parameters =
                    createProjektParameters();

            when(parameters.sgt()).thenReturn("15");

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(siedlungsgebietService)
                    .checkExistsBySiedlungsgebiet(
                            new BigDecimal("15"));
        }

        @Test
        void givenKurzbezeichnung_thenShouldCheckKurzbezeichnung() {
            // Given
            final ReportAuswertungProjekteDTO parameters =
                    createProjektParameters();

            when(parameters.kurz()).thenReturn("KURZ");

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(kurzbezeichnungService)
                    .checkExistsByKurzbezeichnung("KURZ");
        }

        @Test
        void givenBauprogramm_thenShouldCheckBauprogramm() {
            // Given
            final ReportAuswertungProjekteDTO parameters =
                    createProjektParameters();

            when(parameters.bpg()).thenReturn("25");

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportService.generateReportAuswertungProjekt(parameters);

            // Then
            verify(bauprogrammService)
                    .checkExistsByBauprogramm(
                            new BigDecimal("25"));
        }

        @Test
        void givenEmptyFilters_thenShouldNotCheckAnyFilterService() {
            // Given
            final ReportAuswertungProjekteDTO parameters =
                    createProjektParameters();

            when(parameters.fb()).thenReturn("");
            when(parameters.sbl()).thenReturn("");
            when(parameters.bez()).thenReturn("");
            when(parameters.ua()).thenReturn("");
            when(parameters.sgt()).thenReturn("");
            when(parameters.kurz()).thenReturn("");
            when(parameters.bpg()).thenReturn("");

            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(new HashMap<>());

            // When
            reportService.generateReportAuswertungProjekt(parameters);

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
            final ReportAuswertungProjekteDTO parameters =
                    mock(ReportAuswertungProjekteDTO.class);

            when(parameters.fb()).thenReturn("123");

            final NotFoundException exception =
                    new NotFoundException(
                            Foerderbereich.class,
                            "123");

            doThrow(exception)
                    .when(foerderbereichService)
                    .checkExistsByFoerderbereich(new BigDecimal("123"));

            // When
            final Exception thrown =
                    Assertions.assertThrows(
                            NotFoundException.class,
                            () -> reportService.generateReportAuswertungProjekt(parameters));

            // Then
            assertThat(thrown).isSameAs(exception);

            verify(foerderbereichService)
                    .checkExistsByFoerderbereich(new BigDecimal("123"));

            verifyNoInteractions(reportMapper);
        }

    }

    @Nested
    class GetReportAuswertungProjektFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<ReportAuswertungProjektFormContextDTO> projektContext =
                    List.of(
                            mock(ReportAuswertungProjektFormContextDTO.class));

            final List<FoerderbereichFormContextDTO> foerderbereichContext =
                    List.of(
                            mock(FoerderbereichFormContextDTO.class));

            final List<ListennameStadtbezirkslisteFormContextDTO> listennameContext =
                    List.of(
                            mock(ListennameStadtbezirkslisteFormContextDTO.class));

            final List<StadtbezirkFormContextDTO> stadtbezirkContext =
                    List.of(
                            mock(StadtbezirkFormContextDTO.class));

            final List<UnterabschnittFormContextDTO> unterabschnittContext =
                    List.of(
                            mock(UnterabschnittFormContextDTO.class));

            final List<KurzbezeichnungFormContextDTO> kurzbezeichnungContext =
                    List.of(
                            mock(KurzbezeichnungFormContextDTO.class));

            final List<BauprogrammFormContextDTO> bauprogrammContext =
                    List.of(
                            mock(BauprogrammFormContextDTO.class));

            final List<SiedlungsgebietFormContextDTO> siedlungsgebietContext =
                    List.of(
                            mock(SiedlungsgebietFormContextDTO.class));

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
            final ReportAuswertungProjektFormContext formContext =
                    reportService.getReportAuswertungProjekt();

            // Then
            verify(projektService)
                    .getReportAuswertungProjektFormContextDTOs();

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

            verify(bauprogrammService)
                    .getBauprogrammFormContextDTOs();

            verify(siedlungsgebietService)
                    .getSiedlungsgebietFormContextDTOs();

            assertThat(formContext).isNotNull();
        }
    }
}
