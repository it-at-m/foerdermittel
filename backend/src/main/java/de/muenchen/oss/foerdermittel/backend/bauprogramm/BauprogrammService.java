package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammMapper;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import java.math.BigDecimal;
import java.util.List;

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
    private final BauprogrammMapper bauprogrammMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Bauprogramm> getBauprogramme(final Pageable pageable) {
        log.info("Get Bauprogramme with Pageable {}", pageable);
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
        final Bauprogramm foundBauprogramm = ServiceUtils.getEntityOrThrowNotFoundException(bauprogrammId, bauprogrammRepository, Bauprogramm.class);
        foundBauprogramm.setBezeichnung(bauprogramm.getBezeichnung());
        log.debug("Update Bauprogramm {}", foundBauprogramm);
        return bauprogrammRepository.update(foundBauprogramm);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteBauprogramm(final BigDecimal bauprogrammId) {
        log.debug("Delete Bauprogramm with ID {}", bauprogrammId);
        ServiceUtils.getEntityOrThrowNotFoundException(bauprogrammId, bauprogrammRepository, Bauprogramm.class);
        bauprogrammRepository.deleteById(bauprogrammId);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public List<BauprogrammFormContextDTO> getBauprogrammFormContextDTOs() {
        return bauprogrammMapper.toFormContext(bauprogrammRepository.findAll());
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public void checkExistsByBauprogramm(final BigDecimal bauprogrammId) {
        ServiceUtils.checkExistsOrThrowNotFoundException(bauprogrammId, bauprogrammRepository, Bauprogramm.class);
    }

}
