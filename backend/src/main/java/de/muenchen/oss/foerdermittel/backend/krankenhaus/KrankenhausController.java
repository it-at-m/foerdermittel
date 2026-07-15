package de.muenchen.oss.foerdermittel.backend.krankenhaus;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausCreateDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausMapper;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausResponseDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausUpdateDTO;
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
@RequestMapping(value = "/krankenhaeuser", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class KrankenhausController {

    private final KrankenhausService krankenhausService;
    private final KrankenhausMapper krankenhausMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<KrankenhausResponseDTO> getKrankenhaeuser(@ParameterObject @PageableDefault(
            sort = "krhname"
    ) final Pageable pageable) {
        final Page<Krankenhaus> pageWithKrankenhaus = krankenhausService.getKrankenhaeuser(pageable);
        final List<KrankenhausResponseDTO> krankenhausResponseDTOList = pageWithKrankenhaus.getContent().stream()
                .map(krankenhausMapper::toDTO)
                .toList();
        return new PageImpl<>(krankenhausResponseDTOList, pageWithKrankenhaus.getPageable(), pageWithKrankenhaus.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public KrankenhausFormContext getKrankenhausFormContext() {
        return krankenhausService.getKrankenhausFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KrankenhausResponseDTO createKrankenhaus(@Valid @RequestBody final KrankenhausCreateDTO krankenhausCreateDTO) {
        return krankenhausMapper.toDTO(krankenhausService.createKrankenhaus(krankenhausMapper.toEntity(krankenhausCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KrankenhausResponseDTO updateKrankenhaus(@Valid @RequestBody final KrankenhausUpdateDTO krankenhausUpdateDTO,
            @PathVariable("id") final String krankenhausId) {
        return krankenhausMapper
                .toDTO(krankenhausService.updateKrankenhaus(krankenhausMapper.toEntity(krankenhausUpdateDTO), krankenhausId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteKrankenhaus(@PathVariable("id") final String krankenhausId) {
        krankenhausService.deleteKrankenhaus(krankenhausId);
    }

}
