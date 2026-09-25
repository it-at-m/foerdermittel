package de.muenchen.oss.foerdermittel.backend.hhplan;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.hhplan.Hhplan;
import de.muenchen.oss.foerdermittel.backend.hhplan.HhplanFormContext;
import de.muenchen.oss.foerdermittel.backend.hhplan.HhplanService;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanMapper;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanResponseDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/hhplan", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class HhplanController {

    private final HhplanService hhplanService;
    private final HhplanMapper hhplanMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<HhplanResponseDTO> getHhplan(@ParameterObject @PageableDefault(
            sort = "id.hhjJahr"
    ) final Pageable pageable) {
        final Page<Hhplan> pageWithHhplan = hhplanService.getHhplan(pageable);
        final List<HhplanResponseDTO> hhplanResponseDTOList = pageWithHhplan.getContent().stream()
                .map(hhplanMapper::toDTO)
                .toList();
        return new PageImpl<>(hhplanResponseDTOList, pageWithHhplan.getPageable(), pageWithHhplan.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public HhplanFormContext getHhplanFormContext() {
        return hhplanService.getHhplanFormContext();
    }







}
