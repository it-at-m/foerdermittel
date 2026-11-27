package de.muenchen.oss.foerdermittel.backend.hhjahr;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.hhjahr.dto.HhjahrMapper;
import de.muenchen.oss.foerdermittel.backend.hhjahr.dto.HhjahrResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/hhjahre", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class HhjahrController {

    private final HhjahrService hhjahrService;
    private final HhjahrMapper hhjahrMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<HhjahrResponseDTO> getHhjahre(@ParameterObject @PageableDefault(sort = "hhjahr") final Pageable pageable) {

        final Page<Hhjahr> pageWithHhjahr = hhjahrService.getHhjahre(pageable);

        final List<HhjahrResponseDTO> hhjahrResponseDTOList = pageWithHhjahr.getContent()
                .stream()
                .map(hhjahrMapper::toDTO)
                .toList();

        return new PageImpl<>(
                hhjahrResponseDTOList,
                pageWithHhjahr.getPageable(),
                pageWithHhjahr.getTotalElements());
    }
}
