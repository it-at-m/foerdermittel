package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameCreateDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteResponseDTO;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class StadtbezirkslisteMapperTest {

    private final ListennameStadtbezirkslisteMapper stadtbezirkslisteMapper = Mappers.getMapper(ListennameStadtbezirkslisteMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Listenname entity = new Listenname("SL1", "Test", List.of());

            // when
            StadtbezirkslisteResponseDTO dto = stadtbezirkslisteMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getKurzbez());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            ListennameCreateDTO dto = new ListennameCreateDTO("SL1", "Test 1", List.of());

            // when
            Listenname entity = stadtbezirkslisteMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getKurzbez()).isEqualTo(dto.kurzbez());
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            ListennameUpdateDTO dto = new ListennameUpdateDTO("SL1", List.of());

            // when
            Listenname entity = stadtbezirkslisteMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

    @Nested
    class ToFormContext {

        @Test
        void givenEntityList_thenReturnsCorrectFormContextDTOList() {
            // given
            Listenname entity1 = new Listenname("SL1", "Test 1", List.of());
            Listenname entity2 = new Listenname("SL2", "Test 2", List.of());

            List<Listenname> entities = List.of(entity1, entity2);

            // when
            List<ListennameStadtbezirkslisteFormContextDTO> dtos = stadtbezirkslisteMapper.toFormContext(entities);

            // then
            assertThat(dtos).hasSize(2);

            assertThat(dtos.get(0).kurzbez()).isEqualTo(entity1.getKurzbez());
            assertThat(dtos.get(0).bezeichnung()).isEqualTo(entity1.getBezeichnung());

            assertThat(dtos.get(1).kurzbez()).isEqualTo(entity2.getKurzbez());
            assertThat(dtos.get(1).bezeichnung()).isEqualTo(entity2.getBezeichnung());
        }
    }

}
