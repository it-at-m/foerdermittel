package de.muenchen.oss.foerdermittel.backend.report.projektuebersicht;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.projektuebersicht.dto.ReportProjektuebersichtDTO;
import de.muenchen.oss.foerdermittel.backend.report.projektuebersicht.dto.ReportProjektuebersichtMapper;
import de.muenchen.oss.foerdermittel.backend.report.projektuebersicht.formcontext.ReportProjektuebersichtFormContext;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportProjektuebersichtService {

    private final ReportProjektuebersichtMapper reportProjektuebersichtMapper;
    private final ReportService reportService;
    private final ProjektService projektService;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportProjektuebersicht(final ReportProjektuebersichtDTO parameters) {
        final Projekt projekt = projektService.getProjekt(parameters.projnr());
        final Map<String, Object> jasperParameters = reportProjektuebersichtMapper.toJasperParameters(parameters);
        jasperParameters.put("P_PNAME", projekt.getPname());
        jasperParameters.put("P_PSTRASSE", projekt.getPstrasse());
        return reportService.generateReport(jasperParameters, ReportType.FMW_PROJEKTE3, ReportFormat.PDF, null);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportProjektuebersichtFormContext getReportProjektuebersichtFormContext() {
        log.info("Get ReportProjektuebersicht form context");
        return new ReportProjektuebersichtFormContext(projektService.getReportProjektuebersichtFormContextDTOs());
    }
}
