package de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsDTO;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto.ReportProjektuebersichtMsMapper;
import de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.formcontext.ReportProjektuebersichtMsFormContext;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportProjektuebersichtMsService {

    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;
    private final StadtbezirkService stadtbezirkService;
    private final ReportService reportService;
    private final ReportProjektuebersichtMsMapper reportProjektuebersichtMsMapper;
    private final FoerderbereichService foerderbereichService;
    private final UnterabschnittService unterabschnittService;
    private final KurzbezeichnungService kurzbezeichnungService;
    private final SiedlungsgebietService siedlungsgebietService;
    private final BauprogrammService bauprogrammService;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportProjektuebersichtMs(
            final ReportProjektuebersichtMsDTO parameters) {
        if (StringUtils.hasText(parameters.sbl())) {
            listennameStadtbezirkslisteService.checkExistsByListenname(parameters.sbl());
        }
        if (StringUtils.hasText(parameters.bez())) {
            stadtbezirkService.checkExistsByStadtbezirk(new BigDecimal(parameters.bez()));
        }

        final String orderBy = parameters.sort().getOrderBy();

        return reportService.generateReport(reportProjektuebersichtMsMapper.toJasperParameters(parameters), ReportType.FMW_BEWILL4, parameters.type(),
                orderBy);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportProjektuebersichtMsFormContext getReportProjektuebersichtMs() {
        log.info("Get ReportProjekteuebersichtMs form context");
        return new ReportProjektuebersichtMsFormContext(
                listennameStadtbezirkslisteService.getlistennameStadtbezirkslisteFormContextDTOs(),
                stadtbezirkService.getStadtbezirkFormContextDTOs(),
                foerderbereichService.getFoerderbereichFormContextDTOs(),
                unterabschnittService.getUnterabschnittFormContextDTOs(),
                kurzbezeichnungService.getKurzbezeichnungFormContextDTOs(),
                List.of(Krisofp.values()),
                siedlungsgebietService.getSiedlungsgebietFormContextDTOs(),
                bauprogrammService.getBauprogrammFormContextDTOs());
    }

}
