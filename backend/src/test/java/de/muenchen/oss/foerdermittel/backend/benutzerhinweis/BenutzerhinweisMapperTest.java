package de.muenchen.oss.foerdermittel.backend.benutzerhinweis;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto.BenutzerhinweisCreateDTO;
import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto.BenutzerhinweisMapper;
import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto.BenutzerhinweisResponseDTO;
import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto.BenutzerhinweisUpdateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class BenutzerhinweisMapperTest {

    private final BenutzerhinweisMapper benutzerhinweisMapper = Mappers.getMapper(BenutzerhinweisMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Benutzerhinweis entity = new Benutzerhinweis("testId", "Test 1", "Test 2", "Test 3");

            // when
            BenutzerhinweisResponseDTO dto = benutzerhinweisMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.viewId()).isEqualTo(entity.getViewId());
            assertThat(dto.funktionsbeschreibung()).isEqualTo(entity.getFunktionsbeschreibung());
            assertThat(dto.bedienung()).isEqualTo(entity.getBedienung());
            assertThat(dto.pruefungVorgaben()).isEqualTo(entity.getPruefungVorgaben());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            BenutzerhinweisCreateDTO dto = new BenutzerhinweisCreateDTO("testId", "Test 1", "Test 2", "Test 3");

            // when
            Benutzerhinweis entity = benutzerhinweisMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getViewId()).isEqualTo(dto.viewId());
            assertThat(entity.getFunktionsbeschreibung()).isEqualTo(dto.funktionsbeschreibung());
            assertThat(entity.getBedienung()).isEqualTo(dto.bedienung());
            assertThat(entity.getPruefungVorgaben()).isEqualTo(dto.pruefungVorgaben());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            BenutzerhinweisUpdateDTO dto = new BenutzerhinweisUpdateDTO("Test 1", "Test 2", "Test 3");

            // when
            Benutzerhinweis entity = benutzerhinweisMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getViewId()).isNull();
            assertThat(entity.getFunktionsbeschreibung()).isEqualTo(dto.funktionsbeschreibung());
            assertThat(entity.getBedienung()).isEqualTo(dto.bedienung());
            assertThat(entity.getPruefungVorgaben()).isEqualTo(dto.pruefungVorgaben());
        }

    }

}
