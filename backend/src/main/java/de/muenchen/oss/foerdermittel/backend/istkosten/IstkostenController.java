package de.muenchen.oss.foerdermittel.backend.istkosten;


import de.muenchen.oss.foerdermittel.backend.istkosten.dto.IstkostenCreateDTO;
import de.muenchen.oss.foerdermittel.backend.istkosten.dto.IstkostenResponseDTO;
import de.muenchen.oss.foerdermittel.backend.istkosten.dto.IstkostenUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.istkosten.dto.IstkostenMapper;
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
import org.springframework.data.domain.Sort.Direction;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/istkosten", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class IstkostenController {

    private final IstkostenService istkostenService;
    private final IstkostenMapper istkostenMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<IstkostenResponseDTO> getIstkostenEintraege(
            @ParameterObject @PageableDefault(sort = { "id.jahr", "id.monat" }, direction = Direction.DESC) final Pageable pageable) {

        final Page<Istkosten> pageWithIstkosten = istkostenService.getIstkostenEintraege(pageable);

        final List<IstkostenResponseDTO> istkostenResponseDTOList = pageWithIstkosten.getContent().stream()
                .map(istkostenMapper::toDTO)
                .toList();

        return new PageImpl<>(
                istkostenResponseDTOList,
                pageWithIstkosten.getPageable(),
                pageWithIstkosten.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public IstkostenFormContext getIstkostenFormContext() {
        return istkostenService.getIstkostenFormContext();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IstkostenResponseDTO createIstkosten(@Valid @RequestBody final IstkostenCreateDTO istkostenCreateDTO) {
        return istkostenMapper.toDTO(istkostenService.createIstkosten(istkostenMapper.toEntity(istkostenCreateDTO), istkostenMapper.toEntity(istkostenCreateDTO).getId().getProjnr()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IstkostenResponseDTO updateIstkosten(@Valid @RequestBody final IstkostenUpdateDTO istkostenUpdateDTO,
                                                @PathVariable("id") final String istkostenId) {
        return istkostenMapper
                .toDTO(istkostenService.updateIstkosten(istkostenMapper.toEntity(istkostenUpdateDTO), istkostenMapper.mapStringToPrimaryKey(istkostenId)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteIstkosten(@PathVariable("id") final String istkostenId) {
        istkostenService.deleteIstkosten(istkostenMapper.mapStringToPrimaryKey(istkostenId));
    }


}
