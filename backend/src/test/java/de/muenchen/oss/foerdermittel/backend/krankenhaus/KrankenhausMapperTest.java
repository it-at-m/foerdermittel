package de.muenchen.oss.foerdermittel.backend.krankenhaus;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausCreateDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausMapper;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausResponseDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausUpdateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class KrankenhausMapperTest {

    private final KrankenhausMapper krankenhausMapper = Mappers.getMapper(KrankenhausMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Krankenhaus entity = new Krankenhaus("K", "test");

            // when
            KrankenhausResponseDTO dto = krankenhausMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getKrhname());
            assertThat(dto.krhname()).isEqualTo(entity.getKrhname());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            KrankenhausCreateDTO dto = new KrankenhausCreateDTO("K", "Test 1");

            // when
            Krankenhaus entity = krankenhausMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getKrhname()).isEqualTo(dto.krhname());
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            KrankenhausUpdateDTO dto = new KrankenhausUpdateDTO("Test 2");

            // when
            Krankenhaus entity = krankenhausMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getKrhname()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

}
