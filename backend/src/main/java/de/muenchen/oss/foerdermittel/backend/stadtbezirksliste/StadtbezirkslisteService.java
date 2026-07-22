package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class StadtbezirkslisteService {


    private final StadtbezirkslisteRepository stadtbezirkslisteRepository;


//    @PreAuthorize(Authorities.HAS_ANY_ROLE)
//    @Transactional(readOnly = true)
//    public List<Stadtbezirksliste> getStadtbezirke(final String kurzbez) {
//
//        log.info("Get Stadtbezirke for Listenname {}", kurzbez);
//
//        return stadtbezirkslisteRepository.findByListenName_Kurzbez(kurzbez);
//    }


//    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
//    public void setStadtbezirke(
//            final String kurzbez,
//            final List<BigDecimal> stadtbezirke) {
//
//        log.debug("Add Stadtbezirke {} for Listenname {}", stadtbezirke, kurzbez);
//
//
//        Listenname listenname = new Listenname();
//        listenname.setKurzbez(kurzbez);
//
//
//        for (BigDecimal bezirk : stadtbezirke) {
//
//            boolean existiert = stadtbezirkslisteRepository
//                    .findByListenName_Kurzbez(kurzbez)
//                    .stream()
//                    .anyMatch(e -> e.getId().getStadtbezirk().equals(bezirk));
//
//
//            if (!existiert) {
//
//                Stadtbezirksliste eintrag = new Stadtbezirksliste();
//
//                eintrag.setId(
//                        new StadtbezirkslistePrimaryKey(
//                                kurzbez,
//                                bezirk
//                        )
//                );
//
//                eintrag.setListenName(listenname);
//
//
//                Stadtbezirk stadtbezirk = new Stadtbezirk();
//                stadtbezirk.setStadtbezirk(bezirk);
//
//                eintrag.setStadtbezirk(stadtbezirk);
//
//
//                stadtbezirkslisteRepository.insert(eintrag);
//            }
//        }
//    }
//
//    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
//    public void deleteStadtbezirk(
//            String kurzbez,
//            BigDecimal stadtbezirk) {
//
//        stadtbezirkslisteRepository
//                .deleteByListenName_KurzbezAndStadtbezirk_Stadtbezirk(
//                        kurzbez,
//                        stadtbezirk);
//    }

}

