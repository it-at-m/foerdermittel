package de.muenchen.oss.foerdermittel.backend.report;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportFortsetzungsantragDTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportHaushalt1DTO;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichworteDTO;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportFortsetzungsantragFormContext;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportHaushalt1FormContext;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportStichworteFormContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RequiredArgsConstructor
@RequestMapping("/report")
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/stichworte")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_PDF_VALUE,
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    }
            )
    )
    public void getReportStichworte(
            @Valid @ModelAttribute final ReportStichworteDTO parameters,
            final HttpServletResponse response)
            throws IOException, SQLException, JRException {
        final GeneratedReport generatedReport = reportService.generateReportStichworte(parameters);
        setMetadata(response, generatedReport);
        generatedReport.writer().write(response.getOutputStream());
    }

    @GetMapping(value = "/stichworte/form-context", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ReportStichworteFormContext getReportStichworteFormContext() {
        return reportService.getReportStichworte();
    }

    @GetMapping("/haushalt1")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_PDF_VALUE,
                                    schema = @Schema(type = "string", format = "binary")
                            ),

                            @Content(
                                    mediaType = CustomReportContentTypes.EXCEL_CONTENT_TYPE,
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    }
            )
    )
    public void getReportHaushalt1(
            @Valid @ModelAttribute final ReportHaushalt1DTO parameters,
            final HttpServletResponse response)
            throws IOException, SQLException, JRException {
        final GeneratedReport generatedReport = reportService.generateReportHaushalt1(parameters);
        setMetadata(response, generatedReport);
        generatedReport.writer().write(response.getOutputStream());
    }

    @GetMapping(value = "/haushalt1/form-context", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ReportHaushalt1FormContext getReportHaushalt1FormContext() {
        return reportService.getReportHaushalt1();
    }

    @GetMapping("/fortsetzungsantrag")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_PDF_VALUE,
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    }
            )
    )

    public void getReportFortsetzungsantrag(
            @Valid @ModelAttribute final ReportFortsetzungsantragDTO parameters,
            final HttpServletResponse response)
            throws IOException, SQLException, JRException {
        final GeneratedReport generatedReport = reportService.generateReportFortsetzungsantrag(parameters);
        setMetadata(response, generatedReport);
        generatedReport.writer().write(response.getOutputStream());
    }

    @GetMapping(value = "/fortsetzungsantrag/form-context", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ReportFortsetzungsantragFormContext getReportFortsetzungsantragFormContext() {
        return reportService.getReportFortsetzungsantrag();
    }

    private static void setMetadata(final HttpServletResponse response, final GeneratedReport generatedReport) {
        response.setContentType(generatedReport.contentType().toString());
        response.setHeader(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + generatedReport.fileName() + "\"");
    }

}
