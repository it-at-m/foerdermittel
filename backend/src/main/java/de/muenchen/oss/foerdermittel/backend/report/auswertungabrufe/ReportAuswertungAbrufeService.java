package de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.referat.ReferatService;
import de.muenchen.oss.foerdermittel.backend.report.GeneratedReport;
import de.muenchen.oss.foerdermittel.backend.report.ReportService;
import de.muenchen.oss.foerdermittel.backend.report.ReportType;
import de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto.ReportAuswertungAbrufeDTO;
import de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto.ReportAuswertungAbrufeMapper;
import de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.formcontext.ReportAuswertungAbrufeFormContext;
import de.muenchen.oss.foerdermittel.backend.report.validation.CommonSearchParameters;
import de.muenchen.oss.foerdermittel.backend.report.validation.ReportSearchParameterValidator;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportAuswertungAbrufeService {

    private final FoerderbereichService foerderbereichService;
    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;
    private final StadtbezirkService stadtbezirkService;
    private final KurzbezeichnungService kurzbezeichnungService;
    private final BauprogrammService bauprogrammService;
    private final UnterabschnittService unterabschnittService;
    private final SiedlungsgebietService siedlungsgebietService;
    private final ReportAuswertungAbrufeMapper reportMapper;
    private final ReportService reportService;
    private final ProjektService projektService;

    private final ReportSearchParameterValidator reportSearchParameterValidator;
    private final ReferatService referatService;
    private final KrankenhausService krankenhausService;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public GeneratedReport generateReportAuswertungAbrufe(
            final ReportAuswertungAbrufeDTO parameters) {

        reportSearchParameterValidator.validateCommonSearchParameters(
                new CommonSearchParameters(
                        parameters.fb(),
                        parameters.sbl(),
                        parameters.bez(),
                        parameters.ua(),
                        parameters.sgt(),
                        parameters.kurz(),
                        parameters.bpg()));
        reportSearchParameterValidator.validateReferat(parameters.ref());
        reportSearchParameterValidator.validateKrankenhaus(parameters.krh());

        final String orderBy = parameters.sort().getOrderBy();

        return reportService.generateReport(
                reportMapper.toJasperParameters(parameters),
                ReportType.FMW_ABRUFE1,
                parameters.type(),
                orderBy);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public ReportAuswertungAbrufeFormContext getReportAuswertungAbrufeFormContext() {
        log.info("Get ReportAuswertungAbrufe form context");

        return new ReportAuswertungAbrufeFormContext(
                listennameStadtbezirkslisteService.getlistennameStadtbezirkslisteFormContextDTOs(),
                stadtbezirkService.getStadtbezirkFormContextDTOs(),
                foerderbereichService.getFoerderbereichFormContextDTOs(),
                unterabschnittService.getUnterabschnittFormContextDTOs(),
                kurzbezeichnungService.getKurzbezeichnungFormContextDTOs(),
                projektService.getReportAuswertungAntragFormContextDTOs(),
                referatService.getReferatFormContextDTOs(),
                krankenhausService.getKrankenhausFormContextDTOs(),
                List.of(Krisofp.values()),
                siedlungsgebietService.getSiedlungsgebietFormContextDTOs(),
                bauprogrammService.getBauprogrammFormContextDTOs());

    }
}
