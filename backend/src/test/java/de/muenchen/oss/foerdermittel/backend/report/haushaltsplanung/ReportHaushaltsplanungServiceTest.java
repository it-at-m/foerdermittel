package de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
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
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung.dto.ReportHaushaltsplanungDTO;
import de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung.dto.ReportHaushaltsplanungMapper;
import de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung.dto.ReportHaushaltsplanungSort;
import de.muenchen.oss.foerdermittel.backend.report.haushaltsplanung.formcontext.ReportHaushaltsplanungFormContext;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteFormContext;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
class ReportHaushaltsplanungServiceTest {

    @Mock
    private StadtbezirkService stadtbezirkService;

    @Mock
    private FoerderbereichService foerderbereichService;

    @Mock
    private ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;

    @Mock
    private HhplanService hhplanService;

    @Mock
    private ReportService reportService;

    @Mock
    private ReportHaushaltsplanungMapper reportHaushaltsplanungMapper;

    @InjectMocks
    private ReportHaushaltsplanungService reportHaushaltsplanungService;

    @Nested
    class GenerateReportHaushaltsplanung {

        @Test
        void givenAllParameters_thenShouldGenerateCorrectGeneratedReport() {
            // Given
            final ReportHaushaltsplanungDTO parameters = new ReportHaushaltsplanungDTO(
                    "2024",
                    "1",
                    "0000.000.123",
                    "1",
                    "1",
                    "1",
                    ReportHaushaltsplanungSort.FB_PROJEKTNUMMER,
                    ReportFormat.PDF);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportHaushaltsplanungMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            final GeneratedReport expectedReport = new GeneratedReport(
                    ReportType.FMW_HAUSHALT1.getFileName() + ReportFormat.PDF.getFileExtension(),
                    ReportFormat.PDF.getContentType(),
                    outputStream -> {
                    });

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_HAUSHALT1,
                    ReportFormat.PDF,
                    ReportHaushaltsplanungSort.FB_PROJEKTNUMMER.getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportHaushaltsplanungService.generateReportHaushaltsplanung(parameters);

            // Then
            verify(foerderbereichService)
                    .checkExistsByFoerderbereich(new BigDecimal("1"));
            verify(listennameStadtbezirkslisteService)
                    .checkExistsByListenname("1");
            verify(stadtbezirkService)
                    .checkExistsByStadtbezirk(new BigDecimal("1"));
            verify(reportHaushaltsplanungMapper)
                    .toJasperParameters(parameters);
            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_HAUSHALT1,
                    ReportFormat.PDF,
                    ReportHaushaltsplanungSort.FB_PROJEKTNUMMER.getOrderBy());

            assertThat(generatedReport).isSameAs(expectedReport);
            assertThat(generatedReport.contentType())
                    .isEqualTo(ReportFormat.PDF.getContentType());
            assertThat(generatedReport.fileName())
                    .startsWith(ReportType.FMW_HAUSHALT1.getFileName())
                    .endsWith(ReportFormat.PDF.getFileExtension());
        }

        @Test
        void givenBlankOptionalParameters_thenShouldNotCheckOptionalParameters() {
            // Given
            final ReportHaushaltsplanungDTO parameters = new ReportHaushaltsplanungDTO(
                    "2024",
                    "",
                    "0000.000.123",
                    " ",
                    "   ",
                    "1",
                    ReportHaushaltsplanungSort.PROJEKTNUMMER,
                    ReportFormat.PDF);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportHaushaltsplanungMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            final GeneratedReport expectedReport = new GeneratedReport(
                    ReportType.FMW_HAUSHALT1.getFileName() + ReportFormat.PDF.getFileExtension(),
                    ReportFormat.PDF.getContentType(),
                    outputStream -> {
                    });

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_HAUSHALT1,
                    ReportFormat.PDF,
                    ReportHaushaltsplanungSort.PROJEKTNUMMER.getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            final GeneratedReport generatedReport = reportHaushaltsplanungService.generateReportHaushaltsplanung(parameters);

            // Then
            verifyNoInteractions(
                    foerderbereichService,
                    listennameStadtbezirkslisteService,
                    stadtbezirkService);

            verify(reportHaushaltsplanungMapper).toJasperParameters(parameters);
            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_HAUSHALT1,
                    ReportFormat.PDF,
                    ReportHaushaltsplanungSort.PROJEKTNUMMER.getOrderBy());

            assertThat(generatedReport).isSameAs(expectedReport);
        }

        @ParameterizedTest
        @EnumSource(ReportHaushaltsplanungSort.class)
        void givenSort_thenShouldUseCorrectOrderBy(
                final ReportHaushaltsplanungSort sort) {

            // Given
            final ReportHaushaltsplanungDTO parameters = new ReportHaushaltsplanungDTO(
                    "2024",
                    "",
                    "0000.000.123",
                    "",
                    "",
                    "1",
                    sort,
                    ReportFormat.PDF);

            final Map<String, Object> jasperParameters = new HashMap<>();
            when(reportHaushaltsplanungMapper.toJasperParameters(parameters))
                    .thenReturn(jasperParameters);

            final GeneratedReport expectedReport = new GeneratedReport(
                    ReportType.FMW_HAUSHALT1.getFileName() + ReportFormat.PDF.getFileExtension(),
                    ReportFormat.PDF.getContentType(),
                    outputStream -> {
                    });

            when(reportService.generateReport(
                    jasperParameters,
                    ReportType.FMW_HAUSHALT1,
                    ReportFormat.PDF,
                    sort.getOrderBy()))
                    .thenReturn(expectedReport);

            // When
            reportHaushaltsplanungService.generateReportHaushaltsplanung(parameters);

            // Then
            verify(reportService).generateReport(
                    jasperParameters,
                    ReportType.FMW_HAUSHALT1,
                    ReportFormat.PDF,
                    sort.getOrderBy());
        }

        @Test
        void givenFoerderbereichNotFound_thenShouldThrowNotFoundException() {
            // Given
            final String fb = "1";
            final ReportHaushaltsplanungDTO parameters = new ReportHaushaltsplanungDTO(
                    "2024",
                    fb,
                    "0000.000.123",
                    "1",
                    "1",
                    "1",
                    ReportHaushaltsplanungSort.FB_PROJEKTNUMMER,
                    ReportFormat.PDF);

            doThrow(new NotFoundException(Foerderbereich.class, fb))
                    .when(foerderbereichService)
                    .checkExistsByFoerderbereich(new BigDecimal(fb));

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> reportHaushaltsplanungService.generateReportHaushaltsplanung(parameters));

            // Then
            verify(foerderbereichService, times(1))
                    .checkExistsByFoerderbereich(new BigDecimal(fb));
            verifyNoInteractions(
                    listennameStadtbezirkslisteService,
                    stadtbezirkService,
                    reportHaushaltsplanungMapper,
                    reportService);

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
            final ReportHaushaltsplanungDTO parameters = new ReportHaushaltsplanungDTO(
                    "2024",
                    "1",
                    "0000.000.123",
                    sbl,
                    "1",
                    "1",
                    ReportHaushaltsplanungSort.FB_PROJEKTNUMMER,
                    ReportFormat.PDF);

            doThrow(new NotFoundException(
                    StadtbezirkslisteFormContext.class,
                    sbl))
                    .when(listennameStadtbezirkslisteService)
                    .checkExistsByListenname(sbl);

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> reportHaushaltsplanungService.generateReportHaushaltsplanung(parameters));

            // Then
            verify(listennameStadtbezirkslisteService, times(1))
                    .checkExistsByListenname(sbl);
            verifyNoInteractions(
                    stadtbezirkService,
                    reportHaushaltsplanungMapper,
                    reportService);

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
            final ReportHaushaltsplanungDTO parameters = new ReportHaushaltsplanungDTO(
                    "2024",
                    "1",
                    "0000.000.123",
                    "1",
                    bez,
                    "1",
                    ReportHaushaltsplanungSort.FB_PROJEKTNUMMER,
                    ReportFormat.PDF);

            doThrow(new NotFoundException(Stadtbezirk.class, bez))
                    .when(stadtbezirkService)
                    .checkExistsByStadtbezirk(new BigDecimal(bez));

            // When
            final Exception exception = Assertions.assertThrows(
                    NotFoundException.class,
                    () -> reportHaushaltsplanungService.generateReportHaushaltsplanung(parameters));

            // Then
            verify(stadtbezirkService, times(1))
                    .checkExistsByStadtbezirk(new BigDecimal(bez));
            verifyNoInteractions(
                    reportHaushaltsplanungMapper,
                    reportService);

            assertThat(exception.getMessage())
                    .isEqualTo(String.format(
                            "The %s with ID %s was not found.",
                            Stadtbezirk.class.getSimpleName(),
                            bez));
        }
    }

    @Nested
    class GetReportHaushaltsplanungFormContext {

        @Test
        void givenEntitiesExists_thenReturnCorrectFormContext() {
            // Given
            final List<StadtbezirkFormContextDTO> allStadtbezirke = List.of(new StadtbezirkFormContextDTO("K", "Test"),
                    new StadtbezirkFormContextDTO("L", "Test 2"), new StadtbezirkFormContextDTO("M", "Test 3"));
            when(stadtbezirkService.getStadtbezirkFormContextDTOs()).thenReturn(allStadtbezirke);

            final List<FoerderbereichFormContextDTO> allFoerderbereiche = List.of(new FoerderbereichFormContextDTO("K", "Test"),
                    new FoerderbereichFormContextDTO("L", "Test 2"), new FoerderbereichFormContextDTO("M", "Test 3"));
            when(foerderbereichService.getFoerderbereichFormContextDTOs()).thenReturn(allFoerderbereiche);

            final List<ListennameStadtbezirkslisteFormContextDTO> allStadtbezirkslisten = List.of(
                    new ListennameStadtbezirkslisteFormContextDTO("K", "Test"),
                    new ListennameStadtbezirkslisteFormContextDTO("L", "Test 2"), new ListennameStadtbezirkslisteFormContextDTO("M", "Test 3"));
            when(listennameStadtbezirkslisteService.getlistennameStadtbezirkslisteFormContextDTOs()).thenReturn(allStadtbezirkslisten);

            final List<HhplanFormContextDTO> allHhplan = List.of(new HhplanFormContextDTO(new BigDecimal(2024), "0000.0000.000.123"),
                    new HhplanFormContextDTO(new BigDecimal(2025), "0000.0000.000.111"),
                    new HhplanFormContextDTO(new BigDecimal(2023), "0000.0000.000.222"));
            when(hhplanService.getHhplanFormContextDTOs()).thenReturn(allHhplan);
            // When
            final ReportHaushaltsplanungFormContext formContext = reportHaushaltsplanungService.getReportHaushaltsplanungFormContext();

            // Then
            verify(stadtbezirkService, times(1)).getStadtbezirkFormContextDTOs();
            assertThat(formContext.bezs()).isEqualTo(allStadtbezirke);
            assertThat(formContext.sbls()).isEqualTo(allStadtbezirkslisten);
            assertThat(formContext.fipos()).isEqualTo(allHhplan);
            assertThat(formContext.fbs()).isEqualTo(allFoerderbereiche);
        }

    }
}
