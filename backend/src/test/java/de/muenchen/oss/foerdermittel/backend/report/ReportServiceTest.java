package de.muenchen.oss.foerdermittel.backend.report;

import static de.muenchen.oss.foerdermittel.backend.report.ReportService.SORT_PARAMETER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.hhplan.HhplanService;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportProjektuebersichtFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportFortsetzungsantragDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportHaushalt1DTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportHaushalt1Sort;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportMapper;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportProjektuebersichtDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichworteDTO;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportFortsetzungsantragFormContext;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportHaushalt1FormContext;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportProjektuebersichtFormContext;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportStichworteFormContext;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteFormContext;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.Stichwortbereich;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.StichwortbereichService;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichFormContextDTO;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private StichwortbereichService stichwortbereichService;

    @Mock
    private StadtbezirkService stadtbezirkService;

    @Mock
    private FoerderbereichService foerderbereichService;

    @Mock
    private ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;

    @Mock
    private HhplanService hhplanService;

    @Mock
    private JasperReportService jasperReportService;

    @Mock
    private ReportMapper reportMapper;

    @Mock
    private ProjektService projektService;

    @InjectMocks
    private ReportService reportService;

    @Nested
    class GenerateReportStichworte {

        @Test
        void givenNoWriteInteraction_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportStichworteDTO parameters = mock(ReportStichworteDTO.class);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            // When
            final GeneratedReport generatedReport = reportService.generateReportStichworte(parameters);

            // Then
            verify(reportMapper, times(1)).toJasperParameters(parameters);
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
        void givenWriteInteraction_thenShouldCallJasperServiceCorrectly() throws JRException, SQLException, IOException {
            // Given
            final ReportStichworteDTO parameters = mock(ReportStichworteDTO.class);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            final OutputStream outputStream = new ByteArrayOutputStream();

            // When
            final GeneratedReport generatedReport = reportService.generateReportStichworte(parameters);
            generatedReport.writer().write(outputStream);

            // Then
            verify(jasperReportService, times(1)).generateReportWithParameters(
                    ReportType.FMW_ABLAGEINDEX,
                    ReportFormat.PDF,
                    jasperParameters,
                    outputStream);
        }

        @Test
        void givenNotFound_thenShouldThrowNotFoundException() {
            // Given
            final String bereich = "test";
            final ReportStichworteDTO parameters = new ReportStichworteDTO(bereich);

            doThrow(new NotFoundException(Stichwortbereich.class, bereich))
                    .when(stichwortbereichService)
                    .checkExistsByBereich(bereich);

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> reportService.generateReportStichworte(parameters));

            // Then
            verify(stichwortbereichService, times(1)).checkExistsByBereich(bereich);
            assertThat(exception.getMessage()).isEqualTo(String.format("The %s with ID %s was not found.", Stichwortbereich.class.getSimpleName(), bereich));
        }

    }

    @Nested
    class GetReportStichworteFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<StichwortbereichFormContextDTO> allBereiche = List.of(new StichwortbereichFormContextDTO("K", "Test"),
                    new StichwortbereichFormContextDTO("L", "Test 2"), new StichwortbereichFormContextDTO("M", "Test 3"));
            when(stichwortbereichService.getStichwortbereichFormContextDTOs()).thenReturn(allBereiche);

            // When
            final ReportStichworteFormContext formContext = reportService.getReportStichworte();

            // Then
            verify(stichwortbereichService, times(1)).getStichwortbereichFormContextDTOs();
            assertThat(formContext.bereiche()).isEqualTo(allBereiche);
        }

    }

    @Nested
    class GenerateReportProjektuebersicht {

        @Test
        void givenExistingProjekt_thenAddsDatabaseValuesToJasperParameters() {
            // Given
            final ReportProjektuebersichtDTO parameters = new ReportProjektuebersichtDTO("P-123", true);
            final Map<String, Object> jasperParameters = new HashMap<>();
            final Projekt projekt = mock(Projekt.class);
            when(reportMapper.toJasperParameters(parameters)).thenReturn(jasperParameters);
            when(projektService.getProjekt(parameters.projnr())).thenReturn(projekt);
            when(projekt.getPname()).thenReturn("Projektname");
            when(projekt.getPstrasse()).thenReturn("Projektstraße 1");

            // When
            final GeneratedReport generatedReport = reportService.generateReportProjektuebersicht(parameters);

            // Then
            assertThat(generatedReport.fileName()).startsWith(ReportType.FMW_PROJEKTE3.getFileName());
            assertThat(jasperParameters)
                    .containsEntry("P_PNAME", "Projektname")
                    .containsEntry("P_PSTRASSE", "Projektstraße 1")
                    .doesNotContainKey(SORT_PARAMETER);
            verify(projektService, times(1)).getProjekt(parameters.projnr());
            verifyNoInteractions(jasperReportService);
        }

        @Test
        void givenGeneratedReportIsWritten_thenUsesProjektuebersichtReport() throws JRException, SQLException, IOException {
            // Given
            final ReportProjektuebersichtDTO parameters = new ReportProjektuebersichtDTO("P-123", false);
            final Map<String, Object> jasperParameters = new HashMap<>();
            final Projekt projekt = mock(Projekt.class);
            final OutputStream outputStream = new ByteArrayOutputStream();
            when(reportMapper.toJasperParameters(parameters)).thenReturn(jasperParameters);
            when(projektService.getProjekt(parameters.projnr())).thenReturn(projekt);

            // When
            reportService.generateReportProjektuebersicht(parameters).writer().write(outputStream);

            // Then
            verify(jasperReportService, times(1)).generateReportWithParameters(
                    ReportType.FMW_PROJEKTE3,
                    ReportFormat.PDF,
                    jasperParameters,
                    outputStream);
        }

        @Test
        void givenMissingProjekt_thenDoesNotGenerateReport() {
            // Given
            final ReportProjektuebersichtDTO parameters = new ReportProjektuebersichtDTO("MISSING", false);
            when(projektService.getProjekt(parameters.projnr()))
                    .thenThrow(new NotFoundException(Projekt.class, parameters.projnr()));

            // When / Then
            Assertions.assertThrows(
                    NotFoundException.class,
                    () -> reportService.generateReportProjektuebersicht(parameters));
            verifyNoInteractions(jasperReportService, reportMapper);
        }
    }

    @Nested
    class GetReportProjektuebersichtFormContext {

        @Test
        void givenProjectsExist_thenReturnsTheirFormContext() {
            // Given
            final List<ReportProjektuebersichtFormContextDTO> projekte = List.of(
                    new ReportProjektuebersichtFormContextDTO("P-123", "Projektname", "Projektstraße 1"));
            when(projektService.getReportProjektuebersichtFormContextDTOs()).thenReturn(projekte);

            // When
            final ReportProjektuebersichtFormContext formContext = reportService.getReportProjektuebersicht();

            // Then
            assertThat(formContext.projekte()).isEqualTo(projekte);
            verify(projektService, times(1)).getReportProjektuebersichtFormContextDTOs();
        }
    }

    @Nested
    class GenerateReportHaushalt1 {

        @Test
        void givenAllParameters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportHaushalt1DTO parameters = new ReportHaushalt1DTO(
                    "2024",
                    "1",
                    "0000.000.123",
                    "1",
                    "1",
                    "1",
                    ReportHaushalt1Sort.FB_PROJEKTNUMMER,
                    ReportFormat.PDF);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            // When
            final GeneratedReport generatedReport = reportService.generateReportHaushalt1(parameters);

            // Then
            verify(foerderbereichService, times(1))
                    .checkExistsByFoerderbereich(new BigDecimal("1"));
            verify(listennameStadtbezirkslisteService, times(1))
                    .checkExistsByListenname("1");
            verify(stadtbezirkService, times(1))
                    .checkExistsByStadtbezirk(new BigDecimal("1"));
            verify(reportMapper, times(1))
                    .toJasperParameters(parameters);
            verifyNoInteractions(jasperReportService);

            assertThat(generatedReport).isNotNull();
            assertThat(generatedReport.contentType())
                    .isEqualTo(ReportFormat.PDF.getContentType());
            assertThat(generatedReport.fileName())
                    .startsWith(ReportType.FMW_HAUSHALT1.getFileName())
                    .endsWith(ReportFormat.PDF.getFileExtension());
            assertThat(jasperParameters)
                    .containsEntry(
                            SORT_PARAMETER,
                            ReportHaushalt1Sort.FB_PROJEKTNUMMER.getOrderBy());
        }

        @Test
        void givenBlankOptionalParameters_thenShouldNotCheckOptionalParameters() {
            // Given
            final ReportHaushalt1DTO parameters = new ReportHaushalt1DTO(
                    "2024",
                    "",
                    "0000.000.123",
                    " ",
                    "   ",
                    "1",
                    ReportHaushalt1Sort.PROJEKTNUMMER,
                    ReportFormat.PDF);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            // When
            final GeneratedReport generatedReport = reportService.generateReportHaushalt1(parameters);

            // Then
            verifyNoInteractions(
                    foerderbereichService,
                    listennameStadtbezirkslisteService,
                    stadtbezirkService,
                    jasperReportService);

            verify(reportMapper, times(1))
                    .toJasperParameters(parameters);

            assertThat(generatedReport).isNotNull();
            assertThat(jasperParameters)
                    .containsEntry(
                            SORT_PARAMETER,
                            ReportHaushalt1Sort.PROJEKTNUMMER.getOrderBy());
        }

        @Test
        void givenWriteInteraction_thenShouldCallJasperServiceCorrectly() throws JRException, SQLException, IOException {
            // Given
            final ReportHaushalt1DTO parameters = new ReportHaushalt1DTO(
                    "2024",
                    "1",
                    "0000.000.123",
                    "1",
                    "1",
                    "1",
                    ReportHaushalt1Sort.FB_PROJEKTNUMMER,
                    ReportFormat.PDF);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            final OutputStream outputStream = new ByteArrayOutputStream();

            // When
            final GeneratedReport generatedReport = reportService.generateReportHaushalt1(parameters);
            generatedReport.writer().write(outputStream);

            // Then
            verify(jasperReportService, times(1)).generateReportWithParameters(
                    ReportType.FMW_HAUSHALT1,
                    ReportFormat.PDF,
                    jasperParameters,
                    outputStream);
        }

        @ParameterizedTest
        @EnumSource(ReportHaushalt1Sort.class)
        void givenSort_thenShouldUseCorrectOrderBy(final ReportHaushalt1Sort sort) {
            // Given
            final ReportHaushalt1DTO parameters = new ReportHaushalt1DTO(
                    "2024",
                    "",
                    "0000.000.123",
                    "",
                    "",
                    "1",
                    sort,
                    ReportFormat.PDF);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            // When
            reportService.generateReportHaushalt1(parameters);

            // Then
            assertThat(jasperParameters)
                    .containsEntry(SORT_PARAMETER, sort.getOrderBy());
        }

        @ParameterizedTest
        @EnumSource(ReportFormat.class)
        void givenReportFormat_thenShouldUseCorrectReportFormat(
                final ReportFormat reportFormat) throws JRException, SQLException, IOException {

            // Given
            final ReportHaushalt1DTO parameters = new ReportHaushalt1DTO(
                    "2024",
                    "",
                    "0000.000.123",
                    "",
                    "",
                    "1",
                    ReportHaushalt1Sort.PROJEKTNUMMER,
                    reportFormat);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            final OutputStream outputStream = new ByteArrayOutputStream();

            // When
            final GeneratedReport generatedReport = reportService.generateReportHaushalt1(parameters);

            generatedReport.writer().write(outputStream);

            // Then
            verify(jasperReportService, times(1)).generateReportWithParameters(
                    ReportType.FMW_HAUSHALT1,
                    reportFormat,
                    jasperParameters,
                    outputStream);

            assertThat(generatedReport.contentType())
                    .isEqualTo(reportFormat.getContentType());

            assertThat(generatedReport.fileName())
                    .startsWith(
                            ReportType.FMW_HAUSHALT1.getFileName()
                                    + reportFormat.getFileSuffix())
                    .endsWith(reportFormat.getFileExtension());
        }

        @Test
        void givenFoerderbereichNotFound_thenShouldThrowNotFoundException() {
            // Given
            final String fb = "1";
            final ReportHaushalt1DTO parameters = new ReportHaushalt1DTO(
                    "2024",
                    fb,
                    "0000.000.123",
                    "1",
                    "1",
                    "1",
                    ReportHaushalt1Sort.FB_PROJEKTNUMMER,
                    ReportFormat.PDF);

            doThrow(new NotFoundException(Foerderbereich.class, fb))
                    .when(foerderbereichService)
                    .checkExistsByFoerderbereich(new BigDecimal(fb));

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> reportService.generateReportHaushalt1(parameters));

            // Then
            verify(foerderbereichService, times(1))
                    .checkExistsByFoerderbereich(new BigDecimal(fb));
            verifyNoInteractions(
                    listennameStadtbezirkslisteService,
                    stadtbezirkService,
                    reportMapper,
                    jasperReportService);

            assertThat(exception.getMessage())
                    .isEqualTo(String.format(
                            "The %s with ID %s was not found.",
                            Foerderbereich.class.getSimpleName(),
                            fb));
        }

        @Test
        void givenListennameStadtbezirkslisteNotFound_thenShouldThrowNotFoundException() {
            // Given
            final String sbl = "1";
            final ReportHaushalt1DTO parameters = new ReportHaushalt1DTO(
                    "2024",
                    "1",
                    "0000.000.123",
                    sbl,
                    "1",
                    "1",
                    ReportHaushalt1Sort.FB_PROJEKTNUMMER,
                    ReportFormat.PDF);

            doThrow(new NotFoundException(
                    StadtbezirkslisteFormContext.class,
                    sbl))
                    .when(listennameStadtbezirkslisteService)
                    .checkExistsByListenname(sbl);

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> reportService.generateReportHaushalt1(parameters));

            // Then
            verify(listennameStadtbezirkslisteService, times(1))
                    .checkExistsByListenname(sbl);
            verifyNoInteractions(
                    stadtbezirkService,
                    reportMapper,
                    jasperReportService);

            assertThat(exception.getMessage())
                    .isEqualTo(String.format(
                            "The %s with ID %s was not found.",
                            StadtbezirkslisteFormContext.class.getSimpleName(),
                            sbl));
        }

        @Test
        void givenStadtbezirkNotFound_thenShouldThrowNotFoundException() {
            // Given
            final String bez = "1";
            final ReportHaushalt1DTO parameters = new ReportHaushalt1DTO(
                    "2024",
                    "1",
                    "0000.000.123",
                    "1",
                    bez,
                    "1",
                    ReportHaushalt1Sort.FB_PROJEKTNUMMER,
                    ReportFormat.PDF);

            doThrow(new NotFoundException(Stadtbezirk.class, bez))
                    .when(stadtbezirkService)
                    .checkExistsByStadtbezirk(new BigDecimal(bez));

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> reportService.generateReportHaushalt1(parameters));

            // Then
            verify(stadtbezirkService, times(1))
                    .checkExistsByStadtbezirk(new BigDecimal(bez));
            verifyNoInteractions(
                    reportMapper,
                    jasperReportService);

            assertThat(exception.getMessage())
                    .isEqualTo(String.format(
                            "The %s with ID %s was not found.",
                            Stadtbezirk.class.getSimpleName(),
                            bez));
        }
    }

    @Nested
    class GetReportHaushalt1FormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<StadtbezirkFormContextDTO> allStadtbezirke = List.of(new StadtbezirkFormContextDTO("K", "Test"),
                    new StadtbezirkFormContextDTO("L", "Test 2"), new StadtbezirkFormContextDTO("M", "Test 3"));
            when(stadtbezirkService.getStadtbezirkFormContextDTOs()).thenReturn(allStadtbezirke);

            final List<FoerderbereichFormContextDTO> allFoerderbereiche = List.of(new FoerderbereichFormContextDTO("K", "Test"),
                    new FoerderbereichFormContextDTO("L", "Test 2"), new FoerderbereichFormContextDTO("M", "Test 3"));
            when(foerderbereichService.getFoerderbereichFormContextDTOs()).thenReturn(allFoerderbereiche);

            final List<ListennameStadtbezirkslisteFormContextDTO> allStadtbezirkslisten = List.of(new ListennameStadtbezirkslisteFormContextDTO("K", "Test"),
                    new ListennameStadtbezirkslisteFormContextDTO("L", "Test 2"), new ListennameStadtbezirkslisteFormContextDTO("M", "Test 3"));
            when(listennameStadtbezirkslisteService.getlistennameStadtbezirkslisteFormContextDTOs()).thenReturn(allStadtbezirkslisten);

            final List<HhplanFormContextDTO> allHhplan = List.of(new HhplanFormContextDTO(new BigDecimal(2024), "0000.0000.000.123"),
                    new HhplanFormContextDTO(new BigDecimal(2025), "0000.0000.000.111"), new HhplanFormContextDTO(new BigDecimal(2023), "0000.0000.000.222"));
            when(hhplanService.getHhplanFormContextDTOs()).thenReturn(allHhplan);
            // When
            final ReportHaushalt1FormContext formContext = reportService.getReportHaushalt1();

            // Then
            verify(stadtbezirkService, times(1)).getStadtbezirkFormContextDTOs();
            assertThat(formContext.bezs()).isEqualTo(allStadtbezirke);
            assertThat(formContext.sbls()).isEqualTo(allStadtbezirkslisten);
            assertThat(formContext.fipos()).isEqualTo(allHhplan);
            assertThat(formContext.fbs()).isEqualTo(allFoerderbereiche);
        }

    }

    @Nested
    class GenerateReportFortsetzungsantrag {

        @Test
        void givenAllParameters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportFortsetzungsantragDTO parameters = new ReportFortsetzungsantragDTO(
                    "1",
                    "1",
                    "1",
                    "1",
                    ReportFormat.PDF);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            // When
            final GeneratedReport generatedReport = reportService.generateReportFortsetzungsantrag(parameters);

            // Then
            verify(listennameStadtbezirkslisteService, times(1))
                    .checkExistsByListenname("1");
            verify(stadtbezirkService, times(1))
                    .checkExistsByStadtbezirk(new BigDecimal("1"));
            verify(reportMapper, times(1))
                    .toJasperParameters(parameters);
            verifyNoInteractions(jasperReportService);

            assertThat(generatedReport).isNotNull();
            assertThat(generatedReport.contentType())
                    .isEqualTo(ReportFormat.PDF.getContentType());
            assertThat(generatedReport.fileName())
                    .startsWith(ReportType.FMW_BEWILL4.getFileName())
                    .endsWith(ReportFormat.PDF.getFileExtension());
            assertThat(jasperParameters)
                    .containsEntry(
                            SORT_PARAMETER,
                            "order by v_fob_fb asc, v_projnr asc, v_bdatum asc");
        }
    }

    @Nested
    class GetReportFortsetzungsantragFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<StadtbezirkFormContextDTO> allStadtbezirke = List.of(new StadtbezirkFormContextDTO("1", "Test"),
                    new StadtbezirkFormContextDTO("2", "Test 2"), new StadtbezirkFormContextDTO("3", "Test 3"));
            when(stadtbezirkService.getStadtbezirkFormContextDTOs()).thenReturn(allStadtbezirke);

            // When
            final ReportFortsetzungsantragFormContext formContext = reportService.getReportFortsetzungsantrag();

            // Then
            verify(stadtbezirkService, times(1)).getStadtbezirkFormContextDTOs();
            assertThat(formContext.bezs()).isEqualTo(allStadtbezirke);
        }

    }
}
