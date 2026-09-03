package de.muenchen.oss.foerdermittel.backend.report;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;

/**
 * Functional interface to pass the output stream write operation to the controller.
 */
@FunctionalInterface
public interface ReportWriter {
    void write(OutputStream outputStream)
            throws IOException, SQLException, JRException;
}
