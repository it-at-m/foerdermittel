package de.muenchen.oss.foerdermittel.backend.report;

import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {
    FMW_ABLAGEINDEX(Set.of(ReportFormat.PDF));

    private final Set<ReportFormat> supportedFormats;

    public boolean supportsFormat(final ReportFormat format) {
        return supportedFormats.contains(format);
    }
}
