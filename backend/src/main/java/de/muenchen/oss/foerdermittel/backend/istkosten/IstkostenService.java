package de.muenchen.oss.foerdermittel.backend.istkosten;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class IstkostenService {

    private final IstkostenRepository istkostenRepository;
    private final ProjektService projektService;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Istkosten> getIstkostenEintraege(final Pageable pageable) {
        log.info("Get Istkosteneintrag with Pageable {}", pageable);
        return istkostenRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public IstkostenFormContext getIstkostenFormContext() {
        log.info("Get Istkosten form context");

        return new IstkostenFormContext(
                istkostenRepository.findAllWithProjekt(),
                projektService.getProjektFormContextDTOs());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Istkosten createIstkosten(final Istkosten istkosten, final String projnr) {
        final Projekt projekt = projektService.getProjekt(projnr);
        istkosten.setProjekt(projekt);

        log.debug("Create Istkosteneintrag {}", istkosten);
        return istkostenRepository.insert(istkosten);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Istkosten updateIstkosten(final Istkosten istkosten, final IstkostenPrimaryKey istkostenId) {
        final Istkosten foundIstkosteneintrag = ServiceUtils.getEntityOrThrowNotFoundException(istkostenId, istkostenRepository, Istkosten.class);

        foundIstkosteneintrag.setIstkosten(istkosten.getIstkosten());

        log.debug("Update Istkosteneintrag {}", foundIstkosteneintrag);
        return istkostenRepository.update(foundIstkosteneintrag);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteIstkosten(final IstkostenPrimaryKey istkostenID) {
        log.debug("Delete Istkosten with ID {}", istkostenID);
        final Istkosten istkosten = ServiceUtils.getEntityOrThrowNotFoundException(istkostenID, istkostenRepository, Istkosten.class);
        istkostenRepository.delete(istkosten);
    }

}
