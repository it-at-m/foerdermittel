package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkResponseDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/stadtbezirkslisten",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class StadtbezirkslisteController {

    private final ListennameService listennameService;
    private final ListennameStadtbezirkslisteMapper listennameStadtbezirkslisteMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkslisteResponseDTO getStadtbezirkliste(@PathVariable final String id) {
        return listennameStadtbezirkslisteMapper.toDTO(listennameService.getListenname(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<StadtbezirkslisteResponseDTO> getStadtbezirklistenByPageable(@ParameterObject @PageableDefault(
            sort = "kurzbez"
    ) final Pageable pageable) {
        final Page<Listenname> pageWithStadtbezirk = listennameService.getAllListennamen(pageable);
        final List<StadtbezirkslisteResponseDTO> stadtbezirkslisteResponseDTOList = pageWithStadtbezirk.getContent().stream()
                .map(listennameStadtbezirkslisteMapper::toDTO)
                .toList();
        return new PageImpl<>(stadtbezirkslisteResponseDTOList, pageWithStadtbezirk.getPageable(), pageWithStadtbezirk.getTotalElements());
    }

//    @PutMapping("/{kurzbez}/stadtbezirke")
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
//    public void setStadtbezirke(
//            @PathVariable final String kurzbez,
//            @RequestBody final List<BigDecimal> stadtbezirke) {
//
//
//        service.setStadtbezirke(kurzbez, stadtbezirke);
//    }
//
//    @DeleteMapping("/{kurzbez}/stadtbezirke/{stadtbezirk}")
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
//    public void deleteStadtbezirk(
//            @PathVariable String kurzbez,
//            @PathVariable BigDecimal stadtbezirk) {
//
//        service.deleteStadtbezirk(kurzbez, stadtbezirk);
//    }
}