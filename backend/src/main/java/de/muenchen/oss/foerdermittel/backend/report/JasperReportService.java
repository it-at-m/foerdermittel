package de.muenchen.oss.foerdermittel.backend.report;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    /// Generates a Jasper report file as byte array given a specific [ReportType], parameters and the
    /// desired [ReportFormat].
    ///
    /// @param reportType requested report type
    /// @param reportFormat requested report format
    /// @param parameters user provided parameters
    /// @param outputStream the output stream to write the generated file content to
    /// @throws IOException when access to the requested report located in the classpath was not possible
    /// @throws SQLException when an error with the database connection occurs
    /// @throws JRException when JasperReport related error (e.g. loading, filling or exporting the
    ///             report) occur
    public void generateReportWithParameters(final ReportType reportType, final ReportFormat reportFormat, final Map<String, Object> parameters,
            final OutputStream outputStream)
            throws IOException, SQLException, JRException {
        checkReportFormat(reportType, reportFormat);

        final JasperReport jasperReport = loadCompiledReport(reportType, reportFormat);

        checkParameters(jasperReport, parameters);

        try (Connection connection = dataSource.getConnection()) {
            final JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection);
            exportReport(jasperPrint, reportFormat, outputStream);
        }
    }

    /// Calculates the file name depending on the desires [ReportType] and [ReportFormat] and uses
    /// timestamps as prefixes
    ///
    /// @param reportType requested report type
    /// @param reportFormat requested report format
    /// @return file name of the file to be generated
    public static String getDownloadFileName(final ReportType reportType, final ReportFormat reportFormat) {
        final String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return reportType.getFileName() + reportFormat.getFileSuffix() + "_" + timestamp + reportFormat.getFileExtension();
    }

    /// Loads a compiled `.jasper` file from class path using a given [ReportType].
    ///
    /// @param reportType type of report to load
    /// @param reportFormat format of the report to generate
    /// @return JasperReport object
    /// @throws IOException when error in file access occurs
    /// @throws JRException when error loading the file content as a JasperReport occurs
    private static JasperReport loadCompiledReport(final ReportType reportType, final ReportFormat reportFormat) throws IOException, JRException {
        final String path = "reports/" + reportType.getFileName() + reportFormat.getFileSuffix() + ".jasper";
        final ClassPathResource resource = new ClassPathResource(path);
        return (JasperReport) JRLoader.loadObject(resource.getURL());
    }

    /// Checks if provided parameters match the expected parameters of a given [JasperReport]
    ///
    /// @param jasperReport the report to check against
    /// @param parameters the provided parameters by the user
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

    /// Checks if a given [ReportFormat] is valid for a given [ReportType].
    ///
    /// @param reportType the ReportType to check against
    /// @param reportFormat the ReportFormat to check
    private static void checkReportFormat(final ReportType reportType, final ReportFormat reportFormat) {
        if (!reportType.supportsFormat(reportFormat)) {
            throw new IllegalArgumentException(
                    "Unsupported ReportFormat: " + reportFormat + " for ReportType: " + reportType);
        }
    }

    /// Exports a filled [JasperPrint] as a byte array to construct `.pdf` or `.xlsx` files.
    ///
    /// @param jasperPrint the filled jasper print object
    /// @param reportFormat the desired ReportFormat
    /// @param outputStream the output stream to write the generated file content to
    /// @throws JRException when error in file export occurs
    private static void exportReport(final JasperPrint jasperPrint, final ReportFormat reportFormat, final OutputStream outputStream) throws JRException {
        switch (reportFormat) {
        case PDF, PDF_FLAT -> JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        case EXCEL -> exportReportToXlsx(jasperPrint, outputStream);
        }
    }

    /// Exports a filled [JasperPrint] as a byte array representing a `.xlsx` file.
    ///
    /// @param jasperPrint the filled jasper print object
    /// @param outputStream the output stream to write the generated file content to
    /// @throws JRException when error in file creation occurs
    private static void exportReportToXlsx(final JasperPrint jasperPrint, final OutputStream outputStream) throws JRException {
        final JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();
    }

}
