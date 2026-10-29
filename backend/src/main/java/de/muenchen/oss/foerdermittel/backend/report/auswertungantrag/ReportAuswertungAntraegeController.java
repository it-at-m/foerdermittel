package de.muenchen.oss.foerdermittel.backend.report.auswertungantrag;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.report.CustomReportContentTypes;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.ReportAuswertungAntrageService;
import de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.dto.ReportAuswertungAntraegeDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.formcontext.ReportAuswertungAntraegeFormContext;
import de.muenchen.oss.foerdermittel.backend.report.auswertungprojekt.formcontext.ReportAuswertungProjektFormContext;
import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/report/auswertungantraege")
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ReportAuswertungAntraegeController {

    private final ReportAuswertungAntraegeService reportAuswertungAntraegeService;

    @GetMapping("/")
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
    public void getReportAuswertungProjekt(
            @Valid @ModelAttribute final ReportAuswertungAntraegeDTO parameters,
            final HttpServletResponse response)
            throws IOException, SQLException, JRException {
        final GeneratedReport generatedReport = reportAuswertungAntraegeService.generateReportAuswertungProjekt(parameters);
        ReportControllerUtil.setMetadata(response, generatedReport);
        generatedReport.writer().write(response.getOutputStream());
    }

    @GetMapping(value = "/form-context", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ReportAuswertungProjektFormContext getReportAuswertungProjektFormContext() {
        return reportAuswertungAntraegeService.getReportAuswertungProjektFormContext();
    }

}
