package de.muenchen.oss.foerdermittel.backend.referat;

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
public class ReferatService {

    private final ReferatRepository referatRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Referat> getReferate(final Pageable pageable) {
        log.info("Get Referate with Pageable {}", pageable);
        return referatRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public ReferatFormContext getReferatFormContext() {
        log.info("Get Referat form context");
        return new ReferatFormContext(referatRepository.findAllRefNr());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Referat createReferat(final Referat referat) {
        log.debug("Create Referat {}", referat);
        return referatRepository.insert(referat);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Referat updateReferat(final Referat referat, final BigDecimal referatId) {
        final Referat foundReferat = ServiceUtils.getEntityOrThrowNotFoundException(referatId, referatRepository);
        foundReferat.setBezeichnung(referat.getBezeichnung());
        log.debug("Update Referat {}", foundReferat);
        return referatRepository.update(foundReferat);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteReferat(final BigDecimal referatId) {
        log.debug("Delete Referat with ID {}", referatId);
        ServiceUtils.getEntityOrThrowNotFoundException(referatId, referatRepository);
        referatRepository.deleteById(referatId);
    }

}
