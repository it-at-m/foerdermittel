package de.muenchen.oss.foerdermittel.backend.report;

import java.util.regex.Pattern;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.repo.DefaultRepositoryService;
import net.sf.jasperreports.repo.RepositoryContext;
import net.sf.jasperreports.repo.Resource;

/**
 * Resolves compiled reports stored in the application's {@code reports} classpath directory.
 *
 * <p>
 * Some legacy compiled reports reference subreports by their file name only. This service
 * resolves every such {@code .jasper} reference relative to {@code reports/} while preserving
 * fully qualified resource locations.
 */
public final class ReportClasspathRepositoryService extends DefaultRepositoryService {

    private static final String REPORTS_DIRECTORY = "reports/";
    private static final Pattern URI_SCHEME = Pattern.compile("^[A-Za-z][A-Za-z0-9+.-]*:");

    public ReportClasspathRepositoryService(final JasperReportsContext jasperReportsContext, final ClassLoader classLoader) {
        super(jasperReportsContext);
        setClassLoader(classLoader);
    }

    @Override
    public <K extends Resource> K getResource(final RepositoryContext repositoryContext, final String location, final Class<K> resourceType) {
        return super.getResource(repositoryContext, resolveLocation(location), resourceType);
    }

    private static String resolveLocation(final String location) {
        if (location != null
                && !location.startsWith(REPORTS_DIRECTORY)
                && !location.startsWith("/")
                && !URI_SCHEME.matcher(location).lookingAt()) {
            return REPORTS_DIRECTORY + location;
        }
        return location;
    }
}
