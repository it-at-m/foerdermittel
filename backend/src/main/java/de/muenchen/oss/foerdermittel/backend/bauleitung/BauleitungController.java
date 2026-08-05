package de.muenchen.oss.foerdermittel.backend.bauleitung;

import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungCreateDTO;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungMapper;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungResponseDTO;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
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
@RequestMapping(value = "/bauleitungen", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class BauleitungController {

    private final BauleitungService bauleitungService;
    private final BauleitungMapper bauleitungMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BauleitungResponseDTO> getBauleitungen(@ParameterObject @PageableDefault(
            sort = "bauleitung"
    ) final Pageable pageable) {
        final Page<Bauleitung> pageWithBauleitung = bauleitungService.getBauleitungen(pageable);
        final List<BauleitungResponseDTO> bauleitungResponseDTOList = pageWithBauleitung.getContent().stream()
                .map(bauleitungMapper::toDTO)
                .toList();
        return new PageImpl<>(bauleitungResponseDTOList, pageWithBauleitung.getPageable(), pageWithBauleitung.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public BauleitungFormContext getBauleitungFormContext() {
        return bauleitungService.getBauleitungFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BauleitungResponseDTO createBauleitung(@Valid @RequestBody final BauleitungCreateDTO bauleitungCreateDTO) {
        return bauleitungMapper.toDTO(bauleitungService.createBauleitung(bauleitungMapper.toEntity(bauleitungCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BauleitungResponseDTO updateBauleitung(@Valid @RequestBody final BauleitungUpdateDTO bauleitungUpdateDTO,
            @PathVariable("id") final String bauleitungId) {
        return bauleitungMapper
                .toDTO(bauleitungService.updateBauleitung(bauleitungMapper.toEntity(bauleitungUpdateDTO), bauleitungId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBauleitung(@PathVariable("id") final String bauleitungId) {
        bauleitungService.deleteBauleitung(bauleitungId);
    }

}
