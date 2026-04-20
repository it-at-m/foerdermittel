package de.muenchen.oss.foerdermittel.backend.domain.krankenhaus;

import de.muenchen.oss.foerdermittel.backend.jsonschemaforms.BaseJsonSchemaFormsController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/krankenhaeuser")
@RequiredArgsConstructor
public class KrankenhausController extends BaseJsonSchemaFormsController<String, KrankenhausReadDto, KrankenhausCreateDto, KrankenhausUpdateDto> {
    private final KrankenhausService krankenhausService;

    @Override
    public List<KrankenhausReadDto> index() {
        return krankenhausService.index();
    }

    @Override
    public KrankenhausUpdateDto get(@PathVariable("id") final String krhname) {
        return krankenhausService.get(krhname);
    }

    @Override
    public void create(@Valid @RequestBody final KrankenhausCreateDto krankenhausCreateForm) {
        krankenhausService.create(krankenhausCreateForm);
    }

    @Override
    public void update(
            @PathVariable("id") final String krhname,
            @Valid @RequestBody final KrankenhausUpdateDto krankenhausUpdateForm
    ) {
        krankenhausService.update(krhname, krankenhausUpdateForm);
    }

    @Override
    public void delete(@PathVariable("id") final String krhname) {
        krankenhausService.delete(krhname);
    }
}
