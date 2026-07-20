package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/listennamen",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class StadtbezirkslisteController {


    private final StadtbezirkslisteService service;


    @GetMapping("/{kurzbez}/stadtbezirke")
    @ResponseStatus(HttpStatus.OK)
    public List<StadtbezirkslisteResponseDTO> getStadtbezirke(
            @PathVariable final String kurzbez) {


        return service.getStadtbezirke(kurzbez)
                .stream()
                .map(eintrag -> {

                    StadtbezirkslisteResponseDTO dto = new StadtbezirkslisteResponseDTO();

                    dto.setListenname(eintrag.getId().getListenname());
                    dto.setStadtbezirk(eintrag.getId().getStadtbezirk());
                    dto.setBezeichnung(eintrag.getBezeichnung());

                    return dto;

                })
                .toList();
    }


    @PutMapping("/{kurzbez}/stadtbezirke")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void setStadtbezirke(
            @PathVariable final String kurzbez,
            @RequestBody final List<BigDecimal> stadtbezirke) {


        service.setStadtbezirke(kurzbez, stadtbezirke);
    }

    @DeleteMapping("/{kurzbez}/stadtbezirke/{stadtbezirk}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteStadtbezirk(
            @PathVariable String kurzbez,
            @PathVariable BigDecimal stadtbezirk) {

        service.deleteStadtbezirk(kurzbez, stadtbezirk);
    }
}