package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkResponseDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.*;
import de.muenchen.oss.foerdermittel.backend.util.ControllerUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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

    private final ListennameStadtbezirkslisteMapper listennameStadtbezirkslisteMapper;
    private final StadtbezirkslisteService stadtbezirkslisteService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkslisteResponseDTO getStadtbezirkliste(@PathVariable final String id) {
        return listennameStadtbezirkslisteMapper.toDTO(stadtbezirkslisteService.getListenname(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<StadtbezirkslisteResponseDTO> getStadtbezirklistenByPageable(@ParameterObject @PageableDefault(
            sort = "kurzbez"
    ) final Pageable pageable) {
        final Page<Listenname> pageWithStadtbezirk = stadtbezirkslisteService.getAllListennamen(pageable);
        final List<StadtbezirkslisteResponseDTO> stadtbezirkslisteResponseDTOList = pageWithStadtbezirk.getContent().stream()
                .map(listennameStadtbezirkslisteMapper::toDTO)
                .toList();
        return new PageImpl<>(stadtbezirkslisteResponseDTOList, pageWithStadtbezirk.getPageable(), pageWithStadtbezirk.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkslisteFormContext getStadtbezirkslisteFormContext() {
        return stadtbezirkslisteService.getStadtbezirksListeFormContext();
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
//    public Listenname createListenname(
//            @RequestBody final Listenname listenname) {
//
//        return stadtbezirkslisteService.createListenname(ListennameCreateDTO);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public StadtbezirkslisteResponseDTO createListenname(@Valid @RequestBody final ListennameCreateDTO listennameCreateDTO) {
        return listennameStadtbezirkslisteMapper.toDTO(stadtbezirkslisteService.createListenname(listennameStadtbezirkslisteMapper.toEntity(listennameCreateDTO)));
    }

//    @PutMapping("/{kurzBez}")
//    @ResponseStatus(HttpStatus.OK)
//    public Listenname updateListenname(
//            @PathVariable final String kurzBez,
//            @RequestBody final Listenname listenname) {
//
//        return stadtbezirkslisteService.updateListenname(listenname, kurzBez);
//    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkslisteResponseDTO updateListenname(@Valid @RequestBody final ListennameUpdateDTO listennameUpdateDTO,
                                                    @PathVariable("id") final String listennameId) {
        return listennameStadtbezirkslisteMapper
                .toDTO(stadtbezirkslisteService.updateListenname(listennameStadtbezirkslisteMapper.toEntity(listennameUpdateDTO),
                        listennameId));
    }

    @PutMapping("/{kurzbez}/stadtbezirke")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void setStadtbezirke(
            @PathVariable final String kurzbez,

            @RequestBody final List<StadtbezirkslisteAssignmentResponseDTO> stadtbezirke){


        stadtbezirkslisteService.setStadtbezirke(kurzbez, stadtbezirke);
    }

    @DeleteMapping("/{kurzbez}/stadtbezirke/{stadtbezirk}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteStadtbezirk(
            @PathVariable String kurzbez,
            @PathVariable BigDecimal stadtbezirk) {

        stadtbezirkslisteService.deleteStadtbezirk(kurzbez, stadtbezirk);
    }

    @DeleteMapping("/{kurzbez}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteListenname(
            @PathVariable String kurzbez)
             {

        stadtbezirkslisteService.deleteListenname(kurzbez);
    }
}