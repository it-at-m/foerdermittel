package de.muenchen.oss.foerdermittel.backend.publikation;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationCreateDTO;
import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationMapper;
import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationResponseDTO;
import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationUpdateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class PublikationMapperTest {

    private final PublikationMapper publikationMapper = Mappers.getMapper(PublikationMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Publikation entity = new Publikation("K", "test");

            // when
            PublikationResponseDTO dto = publikationMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getKurzform());
            assertThat(dto.kurzform()).isEqualTo(entity.getKurzform());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            PublikationCreateDTO dto = new PublikationCreateDTO("K", "Test 1");

            // when
            Publikation entity = publikationMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getKurzform()).isEqualTo(dto.kurzform());
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            PublikationUpdateDTO dto = new PublikationUpdateDTO("Test 2");

            // when
            Publikation entity = publikationMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getKurzform()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

}
