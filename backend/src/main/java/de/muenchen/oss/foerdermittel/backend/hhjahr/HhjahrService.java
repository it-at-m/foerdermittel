package de.muenchen.oss.foerdermittel.backend.hhjahr;

import de.muenchen.oss.foerdermittel.backend.common.DeleteNotAllowedException;
import de.muenchen.oss.foerdermittel.backend.hhplan.HhplanPrimaryKey;
import de.muenchen.oss.foerdermittel.backend.hhplan.HhplanRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import java.math.BigDecimal;
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
public class HhjahrService {

    private final HhjahrRepository hhjahrRepository;
    private final HhplanRepository hhplanRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Hhjahr getHhjahr(final BigDecimal hhjahr) {
        log.info("Get Haushaltjahr with ha {}", hhjahr);
        return ServiceUtils.getEntityOrThrowNotFoundException(hhjahr, hhjahrRepository, Hhjahr.class);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Hhjahr> getHhjahre(final Pageable pageable) {
        log.info("Get Haushaltsjahre with Pageable {}", pageable);
        return hhjahrRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public HhjahrFormContext getHhjahrFormContext() {
        log.info("Get Haushaltsjahr form context");
        return new HhjahrFormContext(hhjahrRepository.findAllHhjahr());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Hhjahr createHhjahr(final Hhjahr hhjahr) {
        hhjahr.getHhplanlisten().forEach(assignment -> {
            assignment.setHhjJahr(hhjahr);
            assignment.setId(new HhplanPrimaryKey(
                    hhjahr.getHhjahr(),
                    assignment.getProProjnr().getProjnr()));
        });

        log.debug(
                "Create Haushaltsjahr with hhjahr '{}' and hhbezeichnung '{}'",
                hhjahr.getHhjahr(),
                hhjahr.getHhbezeichnung());
        return hhjahrRepository.insert(hhjahr);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Hhjahr updateHhjahr(final Hhjahr hhjahr, final BigDecimal hhjahrId) {
        final Hhjahr foundHhjahr = ServiceUtils.getEntityOrThrowNotFoundException(hhjahrId, hhjahrRepository, Hhjahr.class);
        foundHhjahr.setHhbezeichnung(hhjahr.getHhbezeichnung());
        foundHhjahr.setNotizen(hhjahr.getNotizen());
        foundHhjahr.updateHhplan(hhjahr.getHhplanlisten(), hhjahrId);
        log.debug("Update Haushaltsjahr {}", foundHhjahr);
        return hhjahrRepository.update(foundHhjahr);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteHhjahr(final BigDecimal hhjahrId) {

        log.debug("Delete Haushaltjahr with ID {}", hhjahrId);

        if (hhplanRepository.existsByHhjahr(hhjahrId)) {
            throw new DeleteNotAllowedException(
                    Hhjahr.class, Projekt.class);
        }

        final Hhjahr hhjahr = ServiceUtils.getEntityOrThrowNotFoundException(
                hhjahrId,
                hhjahrRepository, Hhjahr.class);

        hhjahrRepository.delete(hhjahr);
    }
}
