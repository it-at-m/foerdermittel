package de.muenchen.oss.foerdermittel.backend.krankenhaus;

import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausMapper;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
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
public class KrankenhausService {

    private final KrankenhausRepository krankenhausRepository;
    private final KrankenhausMapper krankenhausMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Krankenhaus> getKrankenhaeuser(final Pageable pageable) {
        log.info("Get Krankenhaeuser with Pageable {}", pageable);
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
        final Krankenhaus foundKrankenhaus = ServiceUtils.getEntityOrThrowNotFoundException(krhName, krankenhausRepository, Krankenhaus.class);
        foundKrankenhaus.setBezeichnung(krankenhaus.getBezeichnung());
        log.debug("Update Krankenhaus {}", foundKrankenhaus);
        return krankenhausRepository.update(foundKrankenhaus);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteKrankenhaus(final String krhName) {
        log.debug("Delete Krankenhaus with ID {}", krhName);
        ServiceUtils.getEntityOrThrowNotFoundException(krhName, krankenhausRepository, Krankenhaus.class);
        krankenhausRepository.deleteById(krhName);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public List<KrankenhausFormContextDTO> getKrankenhausFormContextDTOs() {
        return krankenhausMapper.toFormContext(krankenhausRepository.findAll());
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public void checkExistsByKrankenhaus(final String krhname) {
        ServiceUtils.checkExistsOrThrowNotFoundException(krhname, krankenhausRepository, Krankenhaus.class);
    }
}
