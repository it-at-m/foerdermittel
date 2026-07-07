package de.muenchen.oss.foerdermittel.backend.traeger;

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
public class TraegerService {

    private final TraegerRepository traegerRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Traeger getTraeger(final BigDecimal traegerId) {
        log.info("Get Traeger with ID {}", traegerId);
        return ServiceUtils.getEntityOrThrowNotFoundException(traegerId, traegerRepository);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Traeger> getAllTraeger(final Pageable pageable) {
        log.info("Get all Traeger with Pageable {}", pageable);
        return traegerRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public TraegerFormContext getTraegerFormContext() {
        log.info("Get Traeger form context");
        return new TraegerFormContext(traegerRepository.findAllKurzformen());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Traeger createTraeger(final Traeger traeger) {
        log.debug("Create Traeger {}", traeger);
        return traegerRepository.insert(traeger);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Traeger updateTraeger(final Traeger traeger, final BigDecimal traegerId) {
        final Traeger foundTraeger = ServiceUtils.getEntityOrThrowNotFoundException(
                traegerId, traegerRepository);

        foundTraeger.setBezeichnung(traeger.getBezeichnung());
        log.debug("Update Traeger {}", foundTraeger);
        return traegerRepository.update(foundTraeger);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteTraeger(final BigDecimal traegerId) {
        log.debug("Delete Traeger with ID {}", traegerId);
        ServiceUtils.getEntityOrThrowNotFoundException(traegerId, traegerRepository);
        traegerRepository.deleteById(traegerId);
    }
}
