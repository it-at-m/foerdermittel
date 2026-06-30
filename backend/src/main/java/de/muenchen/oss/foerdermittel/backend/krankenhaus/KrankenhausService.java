package de.muenchen.oss.foerdermittel.backend.krankenhaus;

import static de.muenchen.oss.foerdermittel.backend.common.ExceptionMessageConstants.MSG_NOT_FOUND;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
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
        return getKrankenhausOrThrowException(krhName);
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
        final Krankenhaus foundKrankenhaus = getKrankenhausOrThrowException(krhName);
        foundKrankenhaus.setBezeichnung(krankenhaus.getBezeichnung());
        log.debug("Update Krankenhaus {}", foundKrankenhaus);
        return krankenhausRepository.update(foundKrankenhaus);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteKrankenhaus(final String krhName) {
        log.debug("Delete Krankenhaus with ID {}", krhName);
        if (!krankenhausRepository.existsById(krhName)) {
            throw new NotFoundException(String.format(MSG_NOT_FOUND, krhName));
        }
        krankenhausRepository.deleteById(krhName);
    }

    private Krankenhaus getKrankenhausOrThrowException(final String krhName) {
        return krankenhausRepository
                .findById(krhName)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_NOT_FOUND, krhName)));
    }
}
