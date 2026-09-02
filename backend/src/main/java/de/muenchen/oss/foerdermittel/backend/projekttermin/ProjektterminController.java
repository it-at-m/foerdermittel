package de.muenchen.oss.foerdermittel.backend.projekttermin;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.projekttermin.dto.ProjektterminCreateDTO;
import de.muenchen.oss.foerdermittel.backend.projekttermin.dto.ProjektterminMapper;
import de.muenchen.oss.foerdermittel.backend.projekttermin.dto.ProjektterminResponseDTO;
import de.muenchen.oss.foerdermittel.backend.projekttermin.dto.ProjektterminUpdateDTO;
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
@RequestMapping(value = "/projekttermin", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ProjektterminController {

    private final ProjektterminService projektterminService;
    private final ProjektterminMapper projektterminMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProjektterminResponseDTO> getProjekttermine(@ParameterObject @PageableDefault(sort = { "projekt.projnr", "termin" }) final Pageable pageable) {
        final Page<Projekttermin> pageWithArchiv = projektterminService.getProjekttermine(pageable);

        final List<ProjektterminResponseDTO> projektterminResponseDTOList = pageWithArchiv.getContent().stream()
                .map(projektterminMapper::toDTO)
                .toList();

        return new PageImpl<>(
                projektterminResponseDTOList,
                pageWithArchiv.getPageable(),
                pageWithArchiv.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public ProjektterminFormContext getProjektterminFormContext() {
        return projektterminService.getProjektterminFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjektterminResponseDTO createProjekttermin(@Valid @RequestBody final ProjektterminCreateDTO projektterminCreateDTO) {

        final Projekttermin projekttermin = projektterminMapper.toEntity(projektterminCreateDTO);

        return projektterminMapper.toDTO(projektterminService.createProjekttermin(projekttermin, projektterminCreateDTO.projnr()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjektterminResponseDTO updateProjekttermin(
            @Valid @RequestBody final ProjektterminUpdateDTO projektterminUpdateDTO,
            @PathVariable("id") final Long terminID) {

        final Projekttermin projekttermin = projektterminMapper.toEntity(projektterminUpdateDTO);

        return projektterminMapper.toDTO(projektterminService.updateProjekttermin(projekttermin,
                terminID));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProjekttermin(@PathVariable("id") final Long terminID) {
        projektterminService.deleteProjekttermin(terminID);
    }

}
