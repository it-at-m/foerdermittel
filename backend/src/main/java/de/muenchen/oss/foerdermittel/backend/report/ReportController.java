package de.muenchen.oss.foerdermittel.backend.report;

import de.muenchen.oss.foerdermittel.backend.report.dto.ReportStichwortbereicheDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/stichwortbereiche")
    public void getReportStichwortbereiche(
            @Valid @ParameterObject final ReportStichwortbereicheDTO parameters,
            final HttpServletResponse response)
            throws IOException, SQLException, JRException {
        final GeneratedReport generatedReport = reportService.generateReportStichwortbereiche(parameters);
        setMetadata(response, generatedReport);
        generatedReport.writer().write(response.getOutputStream());
    }

    private static void setMetadata(final HttpServletResponse response, final GeneratedReport generatedReport) {
        response.setContentType(generatedReport.contentType().toString());
        response.setHeader(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + generatedReport.fileName() + "\"");
    }

}
