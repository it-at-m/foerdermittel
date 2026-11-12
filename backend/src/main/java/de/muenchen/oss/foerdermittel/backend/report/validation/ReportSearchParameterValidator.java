package de.muenchen.oss.foerdermittel.backend.report.validation;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammService;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.FoerderbereichService;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.KrankenhausService;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungService;
import de.muenchen.oss.foerdermittel.backend.referat.ReferatService;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.SiedlungsgebietService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.ListennameStadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.UnterabschnittService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

/**
 * Validates search parameters used by report services.
 */
@Component
@RequiredArgsConstructor
public class ReportSearchParameterValidator {

    private final FoerderbereichService foerderbereichService;
    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;
    private final StadtbezirkService stadtbezirkService;
    private final UnterabschnittService unterabschnittService;
    private final SiedlungsgebietService siedlungsgebietService;
    private final KurzbezeichnungService kurzbezeichnungService;
    private final BauprogrammService bauprogrammService;
    private final ReferatService referatService;
    private final KrankenhausService krankenhausService;

    public void validateFoerderbereich(final String fb) {
        if (StringUtils.hasText(fb)) {
            foerderbereichService.checkExistsByFoerderbereich(parseBigDecimal(fb));
        }
    }

    public void validateStadtbezirksliste(final String sbl) {
        if (StringUtils.hasText(sbl)) {
            listennameStadtbezirkslisteService.checkExistsByListenname(sbl);
        }
    }

    public void validateStadtbezirk(final String bez) {
        if (StringUtils.hasText(bez)) {
            stadtbezirkService.checkExistsByStadtbezirk(parseBigDecimal(bez));
        }
    }

    public void validateUnterabschnitt(final String ua) {
        if (StringUtils.hasText(ua)) {
            unterabschnittService.checkExistsByUnterabschnitt(ua);
        }
    }

    public void validateSiedlungsgebiet(final String sgt) {
        if (StringUtils.hasText(sgt)) {
            siedlungsgebietService.checkExistsBySiedlungsgebiet(parseBigDecimal(sgt));
        }
    }

    public void validateKurzbezeichnung(final String kurz) {
        if (StringUtils.hasText(kurz)) {
            kurzbezeichnungService.checkExistsByKurzbezeichnung(kurz);
        }
    }

    public void validateBauprogramm(final String bpg) {
        if (StringUtils.hasText(bpg)) {
            bauprogrammService.checkExistsByBauprogramm(parseBigDecimal(bpg));
        }
    }

    public void validateReferat(final String ref) {
        if (StringUtils.hasText(ref)) {
            referatService.checkExistsByReferat(parseBigDecimal(ref));
        }
    }

    public void validateKrankenhaus(final String krh) {
        if (StringUtils.hasText(krh)) {
            krankenhausService.checkExistsByKrankenhaus(krh);
        }
    }

    /**
     * Validates all common search parameters used by evaluation reports.
     */
    public void validateCommonSearchParameters(
            final CommonSearchParameters parameters) {

        validateFoerderbereich(parameters.fb());
        validateStadtbezirksliste(parameters.sbl());
        validateStadtbezirk(parameters.bez());
        validateUnterabschnitt(parameters.ua());
        validateSiedlungsgebiet(parameters.sgt());
        validateKurzbezeichnung(parameters.kurz());
        validateBauprogramm(parameters.bpg());
    }

    private BigDecimal parseBigDecimal(final String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ungültiger numerischer Suchparameter: " + value,
                    e);
        }
    }
}
