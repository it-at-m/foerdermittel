package de.muenchen.oss.foerdermittel.backend.kurzbezeichnungen;

import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungCreateDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungMapper;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungResponseDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungUpdateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class KurzbezeichnungMapperTest {

    private final KurzbezeichnungMapper kurzbezeichnungMapper = Mappers.getMapper(KurzbezeichnungMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Kurzbezeichnung entity = new Kurzbezeichnung("K", "test");

            // when
            KurzbezeichnungResponseDTO dto = kurzbezeichnungMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getKurzbez());
            assertThat(dto.kurzbez()).isEqualTo(entity.getKurzbez());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            KurzbezeichnungCreateDTO dto = new KurzbezeichnungCreateDTO("K", "Test 1");

            // when
            Kurzbezeichnung entity = kurzbezeichnungMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getKurzbez()).isEqualTo(dto.kurzbez());
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            KurzbezeichnungUpdateDTO dto = new KurzbezeichnungUpdateDTO("Test 2");

            // when
            Kurzbezeichnung entity = kurzbezeichnungMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getKurzbez()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }
}
