package de.muenchen.oss.foerdermittel.backend.report;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichworteDTO;
import de.muenchen.oss.foerdermittel.backend.report.formcontext.ReportStichworteFormContext;
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
@RequiredArgsConstructor
@RequestMapping("/report")
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/stichworte")
    @ResponseStatus(HttpStatus.OK)
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

    private static void setMetadata(final HttpServletResponse response, final GeneratedReport generatedReport) {
        response.setContentType(generatedReport.contentType().toString());
        response.setHeader(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + generatedReport.fileName() + "\"");
    }

}
