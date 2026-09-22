package de.muenchen.oss.foerdermittel.backend.report;

import java.util.List;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.repo.RepositoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasperReportsConfiguration {

    @Bean
    public JasperReportsContext jasperReportsContext() {
        final SimpleJasperReportsContext jasperReportsContext = new SimpleJasperReportsContext();
        final ClassLoader classLoader = getApplicationClassLoader();
        final RepositoryService repositoryService = new ReportClasspathRepositoryService(jasperReportsContext, classLoader);
        jasperReportsContext.setExtensions(RepositoryService.class, List.of(repositoryService));
        return jasperReportsContext;
    }

    @Bean
    public JasperFillManager jasperFillManager(final JasperReportsContext jasperReportsContext) {
        return JasperFillManager.getInstance(jasperReportsContext);
    }

    private static ClassLoader getApplicationClassLoader() {
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return contextClassLoader == null ? JasperReportsConfiguration.class.getClassLoader() : contextClassLoader;
    }
}
