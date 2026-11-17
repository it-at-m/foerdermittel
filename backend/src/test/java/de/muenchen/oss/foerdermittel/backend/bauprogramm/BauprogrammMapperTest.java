package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammCreateDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammMapper;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammResponseDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammUpdateDTO;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class BauprogrammMapperTest {

    private final BauprogrammMapper bauprogrammMapper = Mappers.getMapper(BauprogrammMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Bauprogramm entity = new Bauprogramm(BigDecimal.valueOf(1), "test");

            // when
            BauprogrammResponseDTO dto = bauprogrammMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getBauprogramm().toString());
            assertThat(dto.bauprogramm()).isEqualTo(entity.getBauprogramm().intValueExact());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            BauprogrammCreateDTO dto = new BauprogrammCreateDTO(1, "Test 1");

            // when
            Bauprogramm entity = bauprogrammMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getBauprogramm()).isEqualTo(BigDecimal.valueOf(dto.bauprogramm()));
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            BauprogrammUpdateDTO dto = new BauprogrammUpdateDTO("Test 2");

            // when
            Bauprogramm entity = bauprogrammMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getBauprogramm()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

    @Nested
    class ToFormContext {

        @Test
        void givenEntity_thenReturnsCorrectFormContextDTO() {
            // given
            Bauprogramm entity = new Bauprogramm(BigDecimal.valueOf(1), "Test");

            // when
            BauprogrammFormContextDTO dto = bauprogrammMapper.toFormContext(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.bauprogramm()).isEqualTo(entity.getBauprogramm().toString());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

        @Test
        void givenEntityList_thenReturnsCorrectFormContextDTOList() {
            // given
            Bauprogramm entity1 = new Bauprogramm(BigDecimal.valueOf(1), "Test 1");
            Bauprogramm entity2 = new Bauprogramm(BigDecimal.valueOf(2), "Test 2");

            List<Bauprogramm> entities = List.of(entity1, entity2);

            // when
            List<BauprogrammFormContextDTO> dtos = bauprogrammMapper.toFormContext(entities);

            // then
            assertThat(dtos).hasSize(2);

            assertThat(dtos.get(0).bauprogramm()).isEqualTo(entity1.getBauprogramm().toString());
            assertThat(dtos.get(0).bezeichnung()).isEqualTo(entity1.getBezeichnung());

            assertThat(dtos.get(1).bauprogramm()).isEqualTo(entity2.getBauprogramm().toString());
            assertThat(dtos.get(1).bezeichnung()).isEqualTo(entity2.getBezeichnung());
        }
    }

}
