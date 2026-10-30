package de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.ReportAnnahmeAnordnungSapService;
import de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.dto.ReportAnnahmeAnordnungSapDTO;
import de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.formcontext.ReportAnnahmeAnordnungSapFormContext;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/report")
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ReportAnnahmeAnordnungSapController {

    private final ReportAnnahmeAnordnungSapService reportAnnahmeAnordnungSapService;

    @GetMapping("/annahmeAnordnungSap")
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
    public void getReportAnnahmeAnordnungSap(
            @Valid @ModelAttribute final ReportAnnahmeAnordnungSapDTO parameters,
            final HttpServletResponse response)
            throws IOException, SQLException, JRException {
        final GeneratedReport generatedReport = reportAnnahmeAnordnungSapService.generateReportAnnahmeAnordnungSap(parameters);
        ReportControllerUtil.setMetadata(response, generatedReport);
        generatedReport.writer().write(response.getOutputStream());
    }

    @GetMapping(value = "/annahmeAnordnungSap/form-context", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ReportAnnahmeAnordnungSapFormContext getReportAnnahmeAnordnungSapFormContext() {
        return reportAnnahmeAnordnungSapService.getReportAnnahmeAnordnungSap();
    }
}
