package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameCreateDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/stadtbezirkslisten",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class StadtbezirkslisteController {

    private final ListennameStadtbezirkslisteMapper listennameStadtbezirkslisteMapper;
    private final ListennameStadtbezirkslisteService listennameStadtbezirkslisteService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkslisteResponseDTO getStadtbezirkliste(@PathVariable final String id) {
        return listennameStadtbezirkslisteMapper.toDTO(listennameStadtbezirkslisteService.getListenname(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<StadtbezirkslisteResponseDTO> getStadtbezirklistenByPageable(@ParameterObject @PageableDefault(
            sort = "kurzbez"
    ) final Pageable pageable) {
        final Page<Listenname> pageWithStadtbezirk = listennameStadtbezirkslisteService.getAllListennamen(pageable);
        final List<StadtbezirkslisteResponseDTO> stadtbezirkslisteResponseDTOList = pageWithStadtbezirk.getContent().stream()
                .map(listennameStadtbezirkslisteMapper::toDTO)
                .toList();
        return new PageImpl<>(stadtbezirkslisteResponseDTOList, pageWithStadtbezirk.getPageable(), pageWithStadtbezirk.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkslisteFormContext getStadtbezirkslisteFormContext() {
        return listennameStadtbezirkslisteService.getStadtbezirksListeFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public StadtbezirkslisteResponseDTO createListenname(@Valid @RequestBody final ListennameCreateDTO listennameCreateDTO) {
        return listennameStadtbezirkslisteMapper
                .toDTO(listennameStadtbezirkslisteService.createListenname(listennameStadtbezirkslisteMapper.toEntity(listennameCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkslisteResponseDTO updateListenname(
            @Valid @RequestBody final ListennameUpdateDTO listennameUpdateDTO,
            @PathVariable("id") final String listennameId) {

        return listennameStadtbezirkslisteMapper.toDTO(
                listennameStadtbezirkslisteService.updateListenname(
                        listennameStadtbezirkslisteMapper.toEntity(listennameUpdateDTO),
                        listennameId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteListenname(
            @PathVariable("id") final String kurzbez) {

        listennameStadtbezirkslisteService.deleteListenname(kurzbez);
    }
}
