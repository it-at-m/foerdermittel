package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Stadtbezirksliste;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteFormContext;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteService;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteCreateDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteResponseDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/stadtbezirkslisteen", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class StadtbezirkslisteController {

    private final StadtbezirkslisteService stadtbezirkslisteService;
    private final StadtbezirkslisteMapper stadtbezirkslisteMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkslisteResponseDTO getStadtbezirksliste(@PathVariable("id") final String stadtbezirkslisteId) {
        return stadtbezirkslisteMapper.toDTO(stadtbezirkslisteService.getStadtbezirksliste(stadtbezirkslisteId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<StadtbezirkslisteResponseDTO> getStadtbezirkslistenByPageable(@ParameterObject @PageableDefault(
            sort = "lnaKurzbez"
    ) final Pageable pageable) {
        final Page<Stadtbezirksliste> pageWithStadtbezirksliste = stadtbezirkslisteService.getAllStadtbezirkslisten(pageable);
        final List<StadtbezirkslisteResponseDTO> stadtbezirkslisteResponseDTOList = pageWithStadtbezirksliste.getContent().stream()
                .map(stadtbezirkslisteMapper::toDTO)
                .toList();
        return new PageImpl<>(stadtbezirkslisteResponseDTOList, pageWithStadtbezirksliste.getPageable(), pageWithStadtbezirksliste.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkslisteFormContext getStadtbezirkslisteFormContext() {
        return stadtbezirkslisteService.getStadtbezirkslisteFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StadtbezirkslisteResponseDTO createStadtbezirksliste(@Valid @RequestBody final StadtbezirkslisteCreateDTO stadtbezirkslisteCreateDTO) {
        return stadtbezirkslisteMapper.toDTO(stadtbezirkslisteService.createStadtbezirksliste(stadtbezirkslisteMapper.toEntity(stadtbezirkslisteCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkslisteResponseDTO updateStadtbezirksliste(@Valid @RequestBody final StadtbezirkslisteUpdateDTO stadtbezirkslisteUpdateDTO,
            @PathVariable("id") final String stadtbezirkslisteId) {
        return stadtbezirkslisteMapper
                .toDTO(stadtbezirkslisteService.updateStadtbezirksliste(stadtbezirkslisteMapper.toEntity(stadtbezirkslisteUpdateDTO), stadtbezirkslisteId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStadtbezirksliste(@PathVariable("id") final String stadtbezirkslisteId) {
        stadtbezirkslisteService.deleteStadtbezirksliste(stadtbezirkslisteId);
    }

}
