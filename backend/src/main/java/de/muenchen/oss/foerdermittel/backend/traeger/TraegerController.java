package de.muenchen.oss.foerdermittel.backend.traeger;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.traeger.dto.TraegerCreateDTO;
import de.muenchen.oss.foerdermittel.backend.traeger.dto.TraegerMapper;
import de.muenchen.oss.foerdermittel.backend.traeger.dto.TraegerResponseDTO;
import de.muenchen.oss.foerdermittel.backend.traeger.dto.TraegerUpdateDTO;
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
@RequestMapping(value = "/traeger", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class TraegerController {

    private final TraegerService traegerService;
    private final TraegerMapper traegerMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<TraegerResponseDTO> getTraegerByPageable(@ParameterObject @PageableDefault(
            sort = "kurzform"
    ) final Pageable pageable) {
        final Page<Traeger> pageWithTraeger = traegerService.getAllTraeger(pageable);
        final List<TraegerResponseDTO> traegerResponseDTOList = pageWithTraeger.getContent().stream()
                .map(traegerMapper::toDTO)
                .toList();
        return new PageImpl<>(traegerResponseDTOList, pageWithTraeger.getPageable(), pageWithTraeger.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public TraegerFormContext getTraegerFormContext() {
        return traegerService.getTraegerFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TraegerResponseDTO createTraeger(@Valid @RequestBody final TraegerCreateDTO traegerCreateDTO) {
        return traegerMapper.toDTO(traegerService.createTraeger(traegerMapper.toEntity(traegerCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TraegerResponseDTO updateTraeger(@Valid @RequestBody final TraegerUpdateDTO traegerUpdateDTO, @PathVariable("id") final String traegerId) {
        return traegerMapper
                .toDTO(traegerService.updateTraeger(traegerMapper.toEntity(traegerUpdateDTO),
                        ControllerUtils.convertStringToBigDecimal(traegerId)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTraeger(@PathVariable("id") final String traegerId) {
        traegerService.deleteTraeger(ControllerUtils.convertStringToBigDecimal(traegerId));
    }

}
