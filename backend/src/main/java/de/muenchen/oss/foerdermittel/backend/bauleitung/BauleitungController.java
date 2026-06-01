package de.muenchen.oss.foerdermittel.backend.bauleitung;


import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungMapper;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungRequestDTO;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungResponseDTO;
import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/bauleitung", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class BauleitungController {

    private final BauleitungService bauleitungService;
    private final BauleitungMapper bauleitungMapper;

    @GetMapping("{bauleitungId}")
    @ResponseStatus(HttpStatus.OK)
    public BauleitungResponseDTO getBauleitung(@PathVariable("bauleitungId") final String bauleitungId) {
        return bauleitungMapper.toDTO(bauleitungService.getBauleitung(bauleitungId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BauleitungResponseDTO> getBauleitungByPageAndSize(@RequestParam(defaultValue = "0") final int pageNumber,
                                                                      @RequestParam(defaultValue = "10") final int pageSize) {
        Page<Bauleitung> pageWithBauleitung = bauleitungService.getAllBauleitungen(pageNumber, pageSize);
        List<BauleitungResponseDTO> bauleitungResponseDTOList = pageWithBauleitung.getContent().stream()
                .map(bauleitungMapper::toDTO)
                .toList();
        return new PageImpl<>(bauleitungResponseDTOList, pageWithBauleitung.getPageable(), pageWithBauleitung.getTotalElements());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BauleitungResponseDTO saveBauleitung(@Valid @RequestBody final BauleitungRequestDTO bauleitungRequestDTO) {
        return bauleitungMapper.toDTO(bauleitungService.createBauleitung(bauleitungMapper.toEntity(bauleitungRequestDTO)));
    }

    @PutMapping("/{bauleitungId}")
    @ResponseStatus(HttpStatus.OK)
    public BauleitungResponseDTO updateBauleitung(@Valid @RequestBody final BauleitungRequestDTO bauleitungRequestDTO,
                                                     @PathVariable("bauleitungId") final String bauleitungId) {
        return bauleitungMapper.toDTO(bauleitungService.updateBauleitung(bauleitungMapper.toEntity(bauleitungRequestDTO), bauleitungId));
    }

    @DeleteMapping("/{bauleitungId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBauleitung(@PathVariable("bauleitungId") final String bauleitungId) {
        bauleitungService.deleteBauleitung(bauleitungId);
    }
}
