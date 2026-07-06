package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietCreateDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietMapper;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietResponseDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietUpdateDTO;
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
@RequestMapping(value = "/siedlungsgebiete", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class SiedlungsgebietController {

    private final SiedlungsgebietService siedlungsgebietService;
    private final SiedlungsgebietMapper siedlungsgebietMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SiedlungsgebietResponseDTO getSiedlungsgebiet(@PathVariable("id") final String siedlungsgebietId) {
        return siedlungsgebietMapper.toDTO(siedlungsgebietService.getSiedlungsgebiet(ControllerUtils.convertStringToBigDecimal(siedlungsgebietId)));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<SiedlungsgebietResponseDTO> getSiedlungsgebieteByPageable(@ParameterObject @PageableDefault(
            sort = "siedlungsgebiet"
    ) final Pageable pageable) {
        final Page<Siedlungsgebiet> pageWithSiedlungsgebiet = siedlungsgebietService.getAllSiedlungsgebiete(pageable);
        final List<SiedlungsgebietResponseDTO> siedlungsgebietResponseDTOList = pageWithSiedlungsgebiet.getContent().stream()
                .map(siedlungsgebietMapper::toDTO)
                .toList();
        return new PageImpl<>(siedlungsgebietResponseDTOList, pageWithSiedlungsgebiet.getPageable(), pageWithSiedlungsgebiet.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public SiedlungsgebietFormContext getSiedlungsgebietFormContext() {
        return siedlungsgebietService.getSiedlungsgebietFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SiedlungsgebietResponseDTO createSiedlungsgebiet(@Valid @RequestBody final SiedlungsgebietCreateDTO siedlungsgebietCreateDTO) {
        return siedlungsgebietMapper.toDTO(siedlungsgebietService.createSiedlungsgebiet(siedlungsgebietMapper.toEntity(siedlungsgebietCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SiedlungsgebietResponseDTO updateSiedlungsgebiet(@Valid @RequestBody final SiedlungsgebietUpdateDTO siedlungsgebietUpdateDTO,
            @PathVariable("id") final String siedlungsgebietId) {
        return siedlungsgebietMapper
                .toDTO(siedlungsgebietService.updateSiedlungsgebiet(siedlungsgebietMapper.toEntity(siedlungsgebietUpdateDTO),
                        ControllerUtils.convertStringToBigDecimal(siedlungsgebietId)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSiedlungsgebiet(@PathVariable("id") final String siedlungsgebietId) {
        siedlungsgebietService.deleteSiedlungsgebiet(ControllerUtils.convertStringToBigDecimal(siedlungsgebietId));
    }

}
