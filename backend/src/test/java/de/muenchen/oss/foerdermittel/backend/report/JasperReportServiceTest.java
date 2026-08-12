package de.muenchen.oss.foerdermittel.backend.report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetMetaDataImpl;
import javax.sql.rowset.RowSetProvider;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

@ExtendWith(MockitoExtension.class)
class JasperReportServiceTest {

    private static final String BEREICH_PARAMETER = "P_BEREICH";
    private static final String SORT_PARAMETER = "P_SORT";

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private DatabaseMetaData databaseMetaData;

    @InjectMocks
    private JasperReportService unitUnderTest;

    @Test
    void givenCompiledReport_thenContainPBereichStringParameter() throws Exception {
        // Given
        final JasperReport jasperReport = (JasperReport) JRLoader.loadObject(
                new ClassPathResource(
                        ReportType.FMW_ABLAGEINDEX.getJasperFilePath(ReportFormat.PDF))
                        .getURL());

        // Then
        assertThat(jasperReport.getParameters())
                .filteredOn(parameter -> parameter.getName().equals(BEREICH_PARAMETER))
                .singleElement()
                .satisfies(parameter -> assertThat(parameter.getValueClassName())
                        .isEqualTo(String.class.getName()));
    }

    @Test
    void givenCompiledReportAndAllParameters_thenGenerateReport() throws Exception {
        // Given
        mockJdbcConnection();

        final Map<String, Object> parameters = createValidParameters();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // When
        unitUnderTest.generateReportWithParameters(
                ReportType.FMW_ABLAGEINDEX,
                ReportFormat.PDF,
                parameters,
                outputStream);

        // Then
        verify(dataSource, times(1)).getConnection();
        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(connection, times(1)).close();
        verify(preparedStatement, times(1)).executeQuery();
        assertThat(outputStream.toByteArray()).isNotEmpty();
    }

    @Test
    void givenUnsupportedReportFormat_thenThrowIllegalArgumentException() {
        // Given
        final Map<String, Object> parameters = createValidParameters();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // When / Then
        assertThatThrownBy(() -> unitUnderTest.generateReportWithParameters(
                ReportType.FMW_ABLAGEINDEX,
                ReportFormat.PDF_FLAT,
                parameters,
                outputStream))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "Unsupported ReportFormat: " + ReportFormat.PDF_FLAT
                                + " for ReportType: " + ReportType.FMW_ABLAGEINDEX);
    }

    @Test
    void givenMissingParameter_thenThrowIllegalArgumentException() {
        // Given
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(SORT_PARAMETER, "");

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // When / Then
        assertThatThrownBy(() -> unitUnderTest.generateReportWithParameters(
                ReportType.FMW_ABLAGEINDEX,
                ReportFormat.PDF,
                parameters,
                outputStream))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(
                        "Missing required JasperReports parameter: " + BEREICH_PARAMETER);
    }

    @Test
    void givenUnknownParameter_thenThrowIllegalArgumentException() {
        // Given
        final Map<String, Object> parameters = createValidParameters();
        parameters.put("UNKNOWN_PARAMETER", "unknown");

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // When / Then
        assertThatThrownBy(() -> unitUnderTest.generateReportWithParameters(
                ReportType.FMW_ABLAGEINDEX,
                ReportFormat.PDF,
                parameters,
                outputStream))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unknown JasperReports parameter: UNKNOWN_PARAMETER");
    }

    private Map<String, Object> createValidParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(BEREICH_PARAMETER, "Testbereich");
        parameters.put(SORT_PARAMETER, "");
        return parameters;
    }

    private void mockJdbcConnection() throws Exception {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getMetaData()).thenReturn(databaseMetaData);
        when(databaseMetaData.getDatabaseProductName()).thenReturn("PostgreSQL");
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(createResultSet());
    }

    private static CachedRowSet createResultSet() throws Exception {
        final RowSetMetaDataImpl metaData = new RowSetMetaDataImpl();
        metaData.setColumnCount(4);
        metaData.setColumnName(1, "BEREICH");
        metaData.setColumnType(1, Types.VARCHAR);
        metaData.setColumnName(2, "NR");
        metaData.setColumnType(2, Types.VARCHAR);
        metaData.setColumnName(3, "INHALT");
        metaData.setColumnType(3, Types.VARCHAR);
        metaData.setColumnName(4, "STICHWORTE");
        metaData.setColumnType(4, Types.VARCHAR);

        final CachedRowSet resultSet = RowSetProvider.newFactory().createCachedRowSet();

        resultSet.setMetaData(metaData);
        resultSet.moveToInsertRow();
        resultSet.updateString("BEREICH", "Testbereich");
        resultSet.updateString("NR", "1");
        resultSet.updateString("INHALT", "Inhalt");
        resultSet.updateString("STICHWORTE", "Stichworte");
        resultSet.insertRow();
        resultSet.moveToCurrentRow();
        resultSet.beforeFirst();

        return resultSet;
    }
}
