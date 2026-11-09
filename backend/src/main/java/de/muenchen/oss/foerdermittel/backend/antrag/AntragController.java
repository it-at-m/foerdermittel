package de.muenchen.oss.foerdermittel.backend.antrag;

import de.muenchen.oss.foerdermittel.backend.antrag.dto.AntragCreateDTO;
import de.muenchen.oss.foerdermittel.backend.antrag.dto.AntragMapper;
import de.muenchen.oss.foerdermittel.backend.antrag.dto.AntragResponseDTO;
import de.muenchen.oss.foerdermittel.backend.antrag.dto.AntragUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
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
@RequestMapping(value = "/antraege", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class AntragController {

    private final AntragService antragService;
    private final AntragMapper antragMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AntragResponseDTO> getAntraege(
            @ParameterObject @PageableDefault(sort = { "projekt.projnr", "antragsdatum" }) final Pageable pageable) {
        final Page<Antrag> pageWithAntraege = antragService.getAntraege(pageable);

        final List<AntragResponseDTO> antragResponseDTOList = pageWithAntraege.getContent().stream()
                .map(antragMapper::toDTO)
                .toList();

        return new PageImpl<>(
                antragResponseDTOList,
                pageWithAntraege.getPageable(),
                pageWithAntraege.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public AntragFormContext getAntragFormContext() {
        return antragService.getAntragFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AntragResponseDTO createAntrag(@Valid @RequestBody final AntragCreateDTO antragCreateDTO) {
        final Antrag antrag = antragMapper.toEntity(antragCreateDTO);

        return antragMapper.toDTO(antragService.createAntrag(antrag, antragCreateDTO.projnr()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AntragResponseDTO updateAntrag(
            @Valid @RequestBody final AntragUpdateDTO antragUpdateDTO,
            @PathVariable("id") final String antragID) {
        final Antrag antrag = antragMapper.toEntity(antragUpdateDTO);

        return antragMapper.toDTO(antragService.updateAntrag(antrag,
                ControllerUtils.convertStringToLong(antragID)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAntrag(@PathVariable("id") final String antragID) {
        antragService.deleteAntrag(ControllerUtils.convertStringToLong(antragID));
    }

}
