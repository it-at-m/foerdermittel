package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import static de.muenchen.oss.foerdermittel.backend.common.ExceptionMessageConstants.MSG_NOT_FOUND;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
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
public class BauprogrammService {

    private final BauprogrammRepository bauprogrammRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Bauprogramm getBauprogramm(final BigDecimal bauprogrammId) {
        log.info("Get Bauprogramm with ID {}", bauprogrammId);
        return ServiceUtils.getEntityOrThrowNotFoundException(bauprogrammId, bauprogrammRepository);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Bauprogramm> getAllBauprogramme(final Pageable pageable) {
        log.info("Get all Bauprogramme with Pageable {}", pageable);
        return bauprogrammRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public BauprogrammFormContext getBauprogrammFormContext() {
        log.info("Get Bauprogramm form context");
        return new BauprogrammFormContext(bauprogrammRepository.findAllBauprogramme());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Bauprogramm createBauprogramm(final Bauprogramm bauprogramm) {
        log.debug("Create Bauprogramm {}", bauprogramm);
        return bauprogrammRepository.insert(bauprogramm);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Bauprogramm updateBauprogramm(final Bauprogramm bauprogramm, final BigDecimal bauprogrammId) {
        final Bauprogramm foundBauprogramm = ServiceUtils.getEntityOrThrowNotFoundException(bauprogrammId, bauprogrammRepository);
        foundBauprogramm.setBezeichnung(bauprogramm.getBezeichnung());
        log.debug("Update Bauprogramm {}", foundBauprogramm);
        return bauprogrammRepository.update(foundBauprogramm);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteBauprogramm(final BigDecimal bauprogrammId) {
        log.debug("Delete Bauprogramm with ID {}", bauprogrammId);
        if (!bauprogrammRepository.existsById(bauprogrammId)) {
            throw new NotFoundException(String.format(MSG_NOT_FOUND, bauprogrammId));
        }
        bauprogrammRepository.deleteById(bauprogrammId);
    }

}
