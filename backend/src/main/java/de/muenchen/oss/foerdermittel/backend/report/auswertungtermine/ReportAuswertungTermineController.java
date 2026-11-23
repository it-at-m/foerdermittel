package de.muenchen.oss.foerdermittel.backend.report.auswertungtermine;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto.ReportAuswertungTermineDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.formcontext.ReportAuswertungTermineFormContext;
import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/report/auswertungtermine")
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ReportAuswertungTermineController {

    private final ReportAuswertungTermineService reportAuswertungTermineService;

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
                            )
                    }
            )
    )
    public void getReportAuswertungTermine(
            @Valid @ModelAttribute final ReportAuswertungTermineDTO parameters,
            final HttpServletResponse response)
            throws IOException, SQLException, JRException {
        final GeneratedReport generatedReport = reportAuswertungTermineService.generateReportAuswertungTermine(parameters);
        ReportControllerUtil.setMetadata(response, generatedReport);
        generatedReport.writer().write(response.getOutputStream());
    }

    @GetMapping(value = "/form-context", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ReportAuswertungTermineFormContext getReportAuswertungTermineFormContext() {
        return reportAuswertungTermineService.getReportAuswertungTermineFormContext();
    }

}
