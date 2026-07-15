package de.muenchen.oss.foerdermittel.backend.stadtbezirk;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkCreateDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkResponseDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.util.ControllerUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/stadtbezirke", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class StadtbezirkController {

    private final StadtbezirkService stadtbezirkService;
    private final StadtbezirkMapper stadtbezirkMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<StadtbezirkResponseDTO> getStadtbezirke(@ParameterObject @PageableDefault(
            sort = "stadtbezirk"
    ) final Pageable pageable) {
        final Page<Stadtbezirk> pageWithStadtbezirk = stadtbezirkService.getStadtbezirke(pageable);
        final List<StadtbezirkResponseDTO> stadtbezirkResponseDTOList = pageWithStadtbezirk.getContent().stream()
                .map(stadtbezirkMapper::toDTO)
                .toList();
        return new PageImpl<>(stadtbezirkResponseDTOList, pageWithStadtbezirk.getPageable(), pageWithStadtbezirk.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkFormContext getStadtbezirkFormContext() {
        return stadtbezirkService.getStadtbezirkFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StadtbezirkResponseDTO createStadtbezirk(@Valid @RequestBody final StadtbezirkCreateDTO stadtbezirkCreateDTO) {
        return stadtbezirkMapper.toDTO(stadtbezirkService.createStadtbezirk(stadtbezirkMapper.toEntity(stadtbezirkCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StadtbezirkResponseDTO updateStadtbezirk(@Valid @RequestBody final StadtbezirkUpdateDTO stadtbezirkUpdateDTO,
            @PathVariable("id") final String stadtbezirkId) {
        return stadtbezirkMapper
                .toDTO(stadtbezirkService.updateStadtbezirk(stadtbezirkMapper.toEntity(stadtbezirkUpdateDTO),
                        ControllerUtils.convertStringToBigDecimal(stadtbezirkId)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStadtbezirk(@PathVariable("id") final String stadtbezirkId) {
        stadtbezirkService.deleteStadtbezirk(ControllerUtils.convertStringToBigDecimal(stadtbezirkId));
    }

}
