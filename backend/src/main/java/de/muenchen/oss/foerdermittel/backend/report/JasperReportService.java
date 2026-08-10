package de.muenchen.oss.foerdermittel.backend.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JasperReportService {

    private final DataSource dataSource;

    public byte[] generateReportWithParameters(final ReportType reportType, final Map<String, Object> parameters, final ReportFormat format)
            throws IOException, SQLException, JRException {
        final JasperReport jasperReport = loadCompiledReport(reportType);

        final JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                parameters,
                dataSource.getConnection());

        return exportReport(jasperPrint, format);
    }

    public static String getFilename(final ReportType reportType, final ReportFormat reportFormat) {
        final String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return String.format("%s_%s.%s", reportType.getFileName(), timestamp, reportFormat.getFileExtension());
    }

    private static JasperReport loadCompiledReport(final ReportType reportType) throws IOException, JRException {
        final ClassPathResource resource = new ClassPathResource(reportType.getResourcePath());

        return (JasperReport) JRLoader.loadObject(resource.getURL());
    }

    private static byte[] exportReport(final JasperPrint jasperPrint, final ReportFormat format) throws JRException {
        return switch (format) {
        case PDF -> JasperExportManager.exportReportToPdf(jasperPrint); // TODO CHECK IF CUSTOM LAYOUT IS NEEDED
        case EXCEL -> exportReportToXlsx(jasperPrint);
        };
    }

    private static byte[] exportReportToXlsx(final JasperPrint jasperPrint) throws JRException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        final JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        final SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        // CHECK IF CUSTOM LAYOUT IS NEEDED
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(false);
        configuration.setOnePagePerSheet(false);
        exporter.setConfiguration(configuration);

        exporter.exportReport();

        return outputStream.toByteArray();
    }

}
