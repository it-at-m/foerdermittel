package de.muenchen.oss.foerdermittel.backend.benutzerhinweis;

import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BenutzerhinweisService {

    private final BenutzerhinweisRepository benutzerhinweisRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Benutzerhinweis getBenutzerhinweis(final String viewId) {
        log.info("Get Benutzerhinweis with ID {}", viewId);
        return ServiceUtils.getEntityOrThrowNotFoundException(viewId, benutzerhinweisRepository);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Benutzerhinweis createBenutzerhinweis(final Benutzerhinweis benutzerhinweis) {
        log.debug("Create Benutzerhinweis {}", benutzerhinweis);
        return benutzerhinweisRepository.insert(benutzerhinweis);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Benutzerhinweis updateBenutzerhinweis(final Benutzerhinweis benutzerhinweis, final String viewId) {
        final Benutzerhinweis foundBenutzerhinweis = ServiceUtils.getEntityOrThrowNotFoundException(viewId, benutzerhinweisRepository);
        foundBenutzerhinweis.setFunktionsbeschreibung(benutzerhinweis.getFunktionsbeschreibung());
        foundBenutzerhinweis.setBedienung(benutzerhinweis.getBedienung());
        foundBenutzerhinweis.setPruefungVorgaben(benutzerhinweis.getPruefungVorgaben());
        log.debug("Update Benutzerhinweis {}", foundBenutzerhinweis);
        return benutzerhinweisRepository.update(foundBenutzerhinweis);
    }

}
