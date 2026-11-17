package de.muenchen.oss.foerdermittel.backend.kurzbezeichnungen;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungCreateDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungMapper;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungResponseDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungUpdateDTO;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

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

    @Nested
    class ToFormContext {

        @Test
        void givenEntity_thenReturnsCorrectFormContextDTO() {
            // given
            Kurzbezeichnung entity = new Kurzbezeichnung("K", "Test");

            // when
            KurzbezeichnungFormContextDTO dto = kurzbezeichnungMapper.toFormContext(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.kurzbez()).isEqualTo(entity.getKurzbez());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

        @Test
        void givenEntityList_thenReturnsCorrectFormContextDTOList() {
            // given
            Kurzbezeichnung entity1 = new Kurzbezeichnung("K", "Test 1");
            Kurzbezeichnung entity2 = new Kurzbezeichnung("L", "Test 2");

            List<Kurzbezeichnung> entities = List.of(entity1, entity2);

            // when
            List<KurzbezeichnungFormContextDTO> dtos = kurzbezeichnungMapper.toFormContext(entities);

            // then
            assertThat(dtos).hasSize(2);

            assertThat(dtos.get(0).kurzbez()).isEqualTo(entity1.getKurzbez());
            assertThat(dtos.get(0).bezeichnung()).isEqualTo(entity1.getBezeichnung());

            assertThat(dtos.get(1).kurzbez()).isEqualTo(entity2.getKurzbez());
            assertThat(dtos.get(1).bezeichnung()).isEqualTo(entity2.getBezeichnung());
        }
    }
}
