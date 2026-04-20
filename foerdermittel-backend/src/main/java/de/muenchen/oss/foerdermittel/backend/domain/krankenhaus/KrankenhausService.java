package de.muenchen.oss.foerdermittel.backend.domain.krankenhaus;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KrankenhausService {
    private final KrankenhausRepository krankenhausRepository;

    public List<KrankenhausReadDto> index() {
        return krankenhausRepository.findAll(Sort.by("krhname"))
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    public KrankenhausUpdateDto get(final String krhname) {
        return krankenhausRepository.findById(krhname)
                .map(this::toUpdateDto)
                .orElseThrow(() -> new NotFoundException("Krankenhaus not found: " + krhname));
    }

    @Transactional
    public void create(final KrankenhausCreateDto createForm) {
        final Krankenhaus krankenhaus = new Krankenhaus();
        krankenhaus.setKrhname(createForm.getKrhname());
        krankenhaus.setBezeichnung(createForm.getBezeichnung());
        krankenhausRepository.save(krankenhaus);
    }

    @Transactional
    public void update(final String krhname, final KrankenhausUpdateDto updateForm) {
        final Krankenhaus krankenhaus = krankenhausRepository.findById(krhname)
                .orElseThrow(() -> new NotFoundException("Krankenhaus not found: " + krhname));
        krankenhaus.setBezeichnung(updateForm.getBezeichnung());
        krankenhausRepository.save(krankenhaus);
    }

    @Transactional
    public void delete(final String krhname) {
        final Krankenhaus krankenhaus = krankenhausRepository.findById(krhname)
                .orElseThrow(() -> new NotFoundException("Krankenhaus not found: " + krhname));
        krankenhausRepository.delete(krankenhaus);
    }

    private KrankenhausReadDto toReadDto(final Krankenhaus krankenhaus) {
        return new KrankenhausReadDto(
                krankenhaus.getKrhname(),
                krankenhaus.getBezeichnung()
        );
    }

    private KrankenhausUpdateDto toUpdateDto(final Krankenhaus krankenhaus) {
        final KrankenhausUpdateDto updateForm = new KrankenhausUpdateDto();
        updateForm.setBezeichnung(krankenhaus.getBezeichnung());
        return updateForm;
    }
}
