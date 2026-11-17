
package de.muenchen.oss.foerdermittel.backend.hhplan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import de.muenchen.oss.foerdermittel.backend.hhjahr.Hhjahr;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanMapper;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanResponseDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class HhplanMapperTest {

    private final HhplanMapper hhplanMapper = Mappers.getMapper(HhplanMapper.class);

    @Nested
    class ToFormContextDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            Hhplan entity = createHhplan();

            HhplanFormContextDTO dto = hhplanMapper.toFormContext(entity);

            assertThat(dto).isNotNull();
            assertThat(dto.hhjJahr()).isEqualTo(entity.getHhjJahr().getHhjahr());
            assertThat(dto.proProjnr()).isEqualTo(entity.getProProjnr().getProjnr());
        }

        @Test
        void givenList_thenReturnsCorrectDTO() {
            Hhplan entity1 = createHhplan();
            entity1.getHhjJahr().setHhjahr(BigDecimal.valueOf(2024));
            entity1.getProProjnr().setProjnr("3003000");

            Hhplan entity2 = createHhplan();
            entity2.getHhjJahr().setHhjahr(BigDecimal.valueOf(2025));
            entity2.getProProjnr().setProjnr("3003001");

            List<Hhplan> list = List.of(entity1, entity2);

            List<HhplanFormContextDTO> dto = hhplanMapper.toFormContext(list);

            assertThat(dto).hasSize(2);
            assertThat(dto)
                    .extracting(
                            HhplanFormContextDTO::hhjJahr,
                            HhplanFormContextDTO::proProjnr)
                    .containsExactly(
                            tuple(BigDecimal.valueOf(2024), "3003000"),
                            tuple(BigDecimal.valueOf(2025), "3003001"));
        }

        @Test
        void givenEmptyList_thenReturnsEmptyList() {
            List<HhplanFormContextDTO> dto = hhplanMapper.toFormContext(List.of());

            assertThat(dto).isEmpty();
        }
    }

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            Hhplan entity = createHhplan();

            HhplanResponseDTO dto = hhplanMapper.toDTO(entity);

            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo("2025-3003000");
            assertThat(dto.proProjnr()).isEqualTo(entity.getId().getProProjnr());
            assertThat(dto.hhjJahr()).isEqualTo(entity.getHhjJahr().getHhjahr());
            assertThat(dto.fipo()).isEqualTo(entity.getFipo());
            assertThat(dto.notizen()).isEqualTo(entity.getNotizen());
            assertThat(dto.pname()).isEqualTo(entity.getProProjnr().getPname());
            assertThat(dto.pstrasse()).isEqualTo(entity.getProProjnr().getPstrasse());
            assertThat(dto.fobFb()).isNull();
            assertThat(dto.stadtbezirk())
                    .isEqualTo(entity.getProProjnr().getBezStadtbezirk().getStadtbezirk());
            assertThat(dto.listenname()).isNull();
            assertThat(dto.mitteleinplanung()).isEqualTo(entity.getMitteleinplanung());
            assertThat(dto.bewilligungVoj()).isEqualTo(entity.getBewilligungVoj());
            assertThat(dto.bewilligungHhj()).isEqualTo(entity.getBewilligungHhj());
            assertThat(dto.erhaltenVoj()).isEqualTo(entity.getErhaltenVoj());
            assertThat(dto.erhaltenHhj()).isEqualTo(entity.getErhaltenHhj());
            assertThat(dto.baustandaktuell()).isEqualTo(entity.getBaustandaktuell());
            assertThat(dto.an()).isEqualTo(entity.getAn());
            assertThat(dto.n1()).isEqualTo(entity.getN1());
            assertThat(dto.n2()).isEqualTo(entity.getN2());
        }

        @Test
        void givenEntityWithoutStadtbezirk_thenReturnsDTOWithNullStadtbezirkAndListenname() {
            Hhplan entity = createHhplan();
            entity.getProProjnr().setBezStadtbezirk(null);

            HhplanResponseDTO dto = hhplanMapper.toDTO(entity);

            assertThat(dto).isNotNull();
            assertThat(dto.stadtbezirk()).isNull();
            assertThat(dto.listenname()).isNull();
        }

        @Test
        void givenNull_thenReturnsNull() {
            HhplanResponseDTO dto = hhplanMapper.toDTO(null);

            assertThat(dto).isNull();
        }
    }

    @Nested
    class ToEntity {

        @Test
        void givenResponseDTO_thenReturnsCorrectEntity() {
            HhplanResponseDTO dto = createHhplanResponseDTO();

            Hhplan entity = hhplanMapper.toEntity(dto);

            assertThat(entity).isNotNull();
            assertThat(entity.getFipo()).isEqualTo(dto.fipo());
            assertThat(entity.getNotizen()).isEqualTo(dto.notizen());
            assertThat(entity.getMitteleinplanung()).isEqualTo(dto.mitteleinplanung());
            assertThat(entity.getBewilligungVoj()).isEqualTo(dto.bewilligungVoj());
            assertThat(entity.getBewilligungHhj()).isEqualTo(dto.bewilligungHhj());
            assertThat(entity.getErhaltenVoj()).isEqualTo(dto.erhaltenVoj());
            assertThat(entity.getErhaltenHhj()).isEqualTo(dto.erhaltenHhj());
            assertThat(entity.getBaustandaktuell()).isEqualTo(dto.baustandaktuell());
            assertThat(entity.getAn()).isEqualTo(dto.an());
            assertThat(entity.getN1()).isEqualTo(dto.n1());
            assertThat(entity.getN2()).isEqualTo(dto.n2());
            assertThat(entity.getId()).isNull();
            assertThat(entity.getHhjJahr()).isNull();
            assertThat(entity.getProProjnr()).isNull();
        }

        @Test
        void givenNull_thenReturnsNull() {
            Hhplan entity = hhplanMapper.toEntity(null);

            assertThat(entity).isNull();
        }
    }

    private Hhplan createHhplan() {
        Hhjahr hhjahr = new Hhjahr(
                BigDecimal.valueOf(2025),
                "Haushaltsjahr 2025",
                "Testnotiz",
                new ArrayList<>());

        Stadtbezirk stadtbezirk = new Stadtbezirk(
                BigDecimal.ONE,
                "Testbezirk",
                new ArrayList<>());

        Projekt projekt = new Projekt();
        projekt.setProjnr("3003000");
        projekt.setPname("Testprojekt");
        projekt.setPstrasse("Teststraße");
        projekt.setFobFb(null);
        projekt.setBezStadtbezirk(stadtbezirk);

        Hhplan entity = new Hhplan();
        entity.setId(new HhplanPrimaryKey(
                BigDecimal.valueOf(2025),
                "3003000"));
        entity.setFipo("123456");
        entity.setNotizen("Testnotiz");
        entity.setMitteleinplanung(BigDecimal.valueOf(100));
        entity.setBewilligungVoj(BigDecimal.valueOf(200));
        entity.setBewilligungHhj(BigDecimal.valueOf(300));
        entity.setErhaltenVoj(BigDecimal.valueOf(400));
        entity.setErhaltenHhj(BigDecimal.valueOf(500));
        entity.setBaustandaktuell(new BigDecimal("50.00"));
        entity.setAn(BigDecimal.valueOf(600));
        entity.setN1(BigDecimal.valueOf(700));
        entity.setN2(BigDecimal.valueOf(800));
        entity.setHhjJahr(hhjahr);
        entity.setProProjnr(projekt);

        return entity;
    }

    private HhplanResponseDTO createHhplanResponseDTO() {
        return new HhplanResponseDTO(
                "2025-3003000",
                "3003000",
                BigDecimal.valueOf(2025),
                "123456",
                "Testnotiz",
                "Testprojekt",
                "Teststraße",
                BigDecimal.TEN,
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(200),
                BigDecimal.valueOf(300),
                BigDecimal.valueOf(400),
                BigDecimal.valueOf(500),
                new BigDecimal("50.00"),
                BigDecimal.valueOf(600),
                BigDecimal.valueOf(700),
                BigDecimal.valueOf(800),
                BigDecimal.ONE,
                "Testliste",
                null,
                "test",
                null,
                "test");
    }
}
