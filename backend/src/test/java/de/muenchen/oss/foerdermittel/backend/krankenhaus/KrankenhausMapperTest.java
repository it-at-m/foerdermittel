package de.muenchen.oss.foerdermittel.backend.krankenhaus;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausCreateDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausMapper;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausResponseDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausUpdateDTO;
import java.util.List;
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

    @Nested
    class ToFormContext {

        @Test
        void givenEntity_thenReturnsCorrectFormContextDTO() {
            Krankenhaus entity = new Krankenhaus(
                    "K1",
                    "Test");

            KrankenhausFormContextDTO dto = krankenhausMapper.toFormContext(entity);

            assertThat(dto).isNotNull();
            assertThat(dto.krhname()).isEqualTo(entity.getKrhname());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

        @Test
        void givenEntityList_thenReturnsCorrectFormContextDTOList() {
            Krankenhaus entity1 = new Krankenhaus(
                    "K1",
                    "Test 1");

            Krankenhaus entity2 = new Krankenhaus(
                    "K2",
                    "Test 2");

            List<Krankenhaus> entities = List.of(entity1, entity2);

            List<KrankenhausFormContextDTO> dtos = krankenhausMapper.toFormContext(entities);

            assertThat(dtos).hasSize(2);

            assertThat(dtos.get(0).krhname()).isEqualTo(entity1.getKrhname());
            assertThat(dtos.get(0).bezeichnung()).isEqualTo(entity1.getBezeichnung());

            assertThat(dtos.get(1).krhname()).isEqualTo(entity2.getKrhname());
            assertThat(dtos.get(1).bezeichnung()).isEqualTo(entity2.getBezeichnung());
        }
    }

}
