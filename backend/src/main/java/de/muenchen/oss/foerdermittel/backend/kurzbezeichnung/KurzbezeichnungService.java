package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung;

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
public class KurzbezeichnungService {

    private final KurzbezeichnungRepository kurzbezeichnungRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Kurzbezeichnung> getAllKurzbezeichnungen(final Pageable pageable) {
        log.info("Get all Kurzbezeichnungen with Pageable {}", pageable);
        return kurzbezeichnungRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public KurzbezeichnungFormContext getKurzbezeichnungFormContext() {
        log.info("Get Kurzbezeichnung form context");
        return new KurzbezeichnungFormContext(kurzbezeichnungRepository.findAllKurzbezeichnungen());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Kurzbezeichnung createKurzbezeichnung(final Kurzbezeichnung kurzbezeichnung) {
        log.debug("Create Kurzbezeichnung {}", kurzbezeichnung);
        return kurzbezeichnungRepository.insert(kurzbezeichnung);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Kurzbezeichnung updateKurzbezeichnung(final Kurzbezeichnung kurzbezeichnung, final String kurzBez) {
        final Kurzbezeichnung foundKurzbezeichnung = ServiceUtils.getEntityOrThrowNotFoundException(kurzBez, kurzbezeichnungRepository);
        foundKurzbezeichnung.setBezeichnung(kurzbezeichnung.getBezeichnung());
        log.debug("Update Kurzbezeichnung {}", foundKurzbezeichnung);
        return kurzbezeichnungRepository.update(foundKurzbezeichnung);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteKurzbezeichnung(final String kurzBez) {
        log.debug("Delete Kurzbezeichnung with ID {}", kurzBez);
        ServiceUtils.getEntityOrThrowNotFoundException(kurzBez, kurzbezeichnungRepository);
        kurzbezeichnungRepository.deleteById(kurzBez);
    }
}
