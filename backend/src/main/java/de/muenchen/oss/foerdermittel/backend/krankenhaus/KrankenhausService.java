package de.muenchen.oss.foerdermittel.backend.krankenhaus;

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
public class KrankenhausService {

    private final KrankenhausRepository krankenhausRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Krankenhaus getKrankenhaus(final String krhName) {
        log.info("Get Krankenhaus with ID {}", krhName);
        return ServiceUtils.getEntityOrThrowNotFoundException(krhName, krankenhausRepository);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Krankenhaus> getAllKrankenhaeuser(final Pageable pageable) {
        log.info("Get all Krankenhaeuser with Pageable {}", pageable);
        return krankenhausRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public KrankenhausFormContext getKrankenhausFormContext() {
        log.info("Get Krankenhaus form context");
        return new KrankenhausFormContext(krankenhausRepository.findAllKrhNamen());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Krankenhaus createKrankenhaus(final Krankenhaus krankenhaus) {
        log.debug("Create Krankenhaus {}", krankenhaus);
        return krankenhausRepository.insert(krankenhaus);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Krankenhaus updateKrankenhaus(final Krankenhaus krankenhaus, final String krhName) {
        final Krankenhaus foundKrankenhaus = ServiceUtils.getEntityOrThrowNotFoundException(krhName, krankenhausRepository);
        foundKrankenhaus.setBezeichnung(krankenhaus.getBezeichnung());
        log.debug("Update Krankenhaus {}", foundKrankenhaus);
        return krankenhausRepository.update(foundKrankenhaus);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteKrankenhaus(final String krhName) {
        log.debug("Delete Krankenhaus with ID {}", krhName);
        ServiceUtils.getEntityOrThrowNotFoundException(krhName, krankenhausRepository);
        krankenhausRepository.deleteById(krhName);
    }
}
