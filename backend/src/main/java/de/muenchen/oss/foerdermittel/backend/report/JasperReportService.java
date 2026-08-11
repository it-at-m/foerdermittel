package de.muenchen.oss.foerdermittel.backend.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JasperReportService {

    private final DataSource dataSource;

    public byte[] generateReportWithParameters(final ReportType reportType, final Map<String, Object> parameters, final ReportFormat format)
            throws IOException, SQLException, JRException {
        final JasperReport jasperReport = loadCompiledReport(reportType);

        checkParameters(jasperReport, parameters);

        try (Connection connection = dataSource.getConnection()) {
            final JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection);
            return exportReport(jasperPrint, reportFormat);
        }
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

    /**
     * Checks if provided parameters match the expected parameters of a given JasperReport
     *
     * @param jasperReport the report to check against
     * @param parameters the provided parameters by the user
     */
    private static void checkParameters(final JasperReport jasperReport, final Map<String, Object> parameters) {
        final Set<String> definedParameterNames = Arrays.stream(jasperReport.getParameters())
                .filter(parameter -> !parameter.isSystemDefined())
                .map(JRParameter::getName)
                .collect(Collectors.toSet());

        for (final String parameterName : definedParameterNames) {
            if (!parameters.containsKey(parameterName)) {
                throw new IllegalArgumentException(
                        "Missing required JasperReports parameter: " + parameterName);
            }
        }

        for (final String parameterName : parameters.keySet()) {
            if (!definedParameterNames.contains(parameterName)) {
                throw new IllegalArgumentException(
                        "Unknown JasperReports parameter: " + parameterName);
            }
        }
    }

    /**
     * Exports a filled JasperPrint as a byte array to construct `.pdf` or `.xlsx` files.
     *
     * @param jasperPrint the filled jasper print object
     * @param format the desired ReportFormat
     * @return byte array representing the generated file
     * @throws JRException when error in file export occurs
     */
    private static byte[] exportReport(final JasperPrint jasperPrint, final ReportFormat format) throws JRException {
        return switch (format) {
        case PDF -> JasperExportManager.exportReportToPdf(jasperPrint);
        case EXCEL -> exportReportToXlsx(jasperPrint);
        };
    }

    private static byte[] exportReportToXlsx(final JasperPrint jasperPrint) throws JRException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        final JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();

        return outputStream.toByteArray();
    }

}
