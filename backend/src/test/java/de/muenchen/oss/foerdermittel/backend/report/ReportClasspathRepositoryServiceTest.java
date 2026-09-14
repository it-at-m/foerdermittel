package de.muenchen.oss.foerdermittel.backend.report;

import static org.assertj.core.api.Assertions.assertThat;

import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.repo.ReportResource;
import org.junit.jupiter.api.Test;

class ReportClasspathRepositoryServiceTest {

    @Test
    void givenBareCompiledReportName_thenResolvesReportFromReportsClasspathDirectory() {
        // Given
        final SimpleJasperReportsContext jasperReportsContext = new SimpleJasperReportsContext();
        final ReportClasspathRepositoryService unitUnderTest = new ReportClasspathRepositoryService(
                jasperReportsContext,
                getClass().getClassLoader());

        // When
        final ReportResource reportResource = unitUnderTest.getResource(null, "FMW_PROJEKTE3_R_Q2.jasper", ReportResource.class);

        // Then
        assertThat(reportResource)
                .isNotNull()
                .extracting(ReportResource::getReport)
                .isNotNull();
    }

    @Test
    void givenQualifiedCompiledReportName_thenDoesNotAlterItsLocation() {
        // Given
        final SimpleJasperReportsContext jasperReportsContext = new SimpleJasperReportsContext();
        final ReportClasspathRepositoryService unitUnderTest = new ReportClasspathRepositoryService(
                jasperReportsContext,
                getClass().getClassLoader());

        // When
        final ReportResource reportResource = unitUnderTest.getResource(null, "reports/FMW_PROJEKTE3_R_Q2.jasper", ReportResource.class);

        // Then
        assertThat(reportResource)
                .isNotNull()
                .extracting(ReportResource::getReport)
                .isNotNull();
    }
}
