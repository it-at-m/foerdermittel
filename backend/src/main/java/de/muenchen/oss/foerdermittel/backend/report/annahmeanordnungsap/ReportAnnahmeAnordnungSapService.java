package de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.dto.ReportAnnahmeAnordnungSapDTO;
import de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.dto.ReportAnnahmeAnordnungSapMapper;
import de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.formcontext.ReportAnnahmeAnordnungSapFormContext;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportAnnahmeAnordnungSapService {

    private final ReportAnnahmeAnordnungSapMapper reportAnnahmeAnordnungSapMapper;
    private final ReportService reportService;
    private final ProjektService projektService;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportAnnahmeAnordnungSap(final ReportAnnahmeAnordnungSapDTO parameters) {
        final Projekt projekt = projektService.getProjekt(parameters.projnr());
        final Map<String, Object> jasperParameters = reportAnnahmeAnordnungSapMapper.toJasperParameters(parameters);
        jasperParameters.put("P_PNAME", projekt.getPname());
        jasperParameters.put("P_PSTRASSE", projekt.getPstrasse());
        return reportService.generateReport(jasperParameters, ReportType.FMW_ANNAHMESAP, ReportFormat.PDF, null);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportAnnahmeAnordnungSapFormContext getReportAnnahmeAnordnungSap() {
        log.info("Get ReportAnnahmeAnordnungSap form context");
        return new ReportAnnahmeAnordnungSapFormContext(projektService.getReportAnnahmeAnordnungSapFormContextDTOs());
    }
}
