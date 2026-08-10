package de.muenchen.oss.foerdermittel.backend.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportType {
    FMW_ABLAGEINDEX_R("FMW_ABLAGEINDEX_R.jasper");

    private final String fileName;

    public String getResourcePath() {
        return "reports/" + this.fileName;
    }
}
