package de.muenchen.oss.foerdermittel.backend.istkosten;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.istkosten.dto.IstkostenCreateDTO;
import de.muenchen.oss.foerdermittel.backend.istkosten.dto.IstkostenMapper;
import de.muenchen.oss.foerdermittel.backend.istkosten.dto.IstkostenResponseDTO;
import de.muenchen.oss.foerdermittel.backend.istkosten.dto.IstkostenUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class IstkostenMapperTest {

    private final IstkostenMapper istkostenMapper = Mappers.getMapper(IstkostenMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            final Foerderbereich foerderbereich = new Foerderbereich();
            foerderbereich.setFb(BigDecimal.valueOf(123));

            final Projekt projekt = new Projekt();
            projekt.setProjnr("1124101");
            projekt.setPname("Test Projekt");
            projekt.setPstrasse("Teststraße 1");
            projekt.setFoerderbereich(foerderbereich);

            final Istkosten entity = new Istkosten(new IstkostenPrimaryKey(projekt.getProjnr(), new BigDecimal(2026), new BigDecimal(1)), projekt,
                    new BigDecimal(5000));

            final IstkostenResponseDTO dto = istkostenMapper.toDTO(entity);

            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getId().toString());
            assertThat(entity.getId().getProjnr()).isEqualTo(dto.projnr());
            assertThat(entity.getId().getJahr().compareTo(dto.jahr()));
            assertThat(entity.getId().getMonat().compareTo(dto.monat()));
            assertThat(entity.getIstkosten().compareTo(dto.istkosten()));
            assertThat(dto.pname()).isEqualTo(entity.getProjekt().getPname());
            assertThat(dto.pstrasse()).isEqualTo(entity.getProjekt().getPstrasse());
            assertThat(dto.fob_fb()).isEqualTo(entity.getProjekt().getFoerderbereich().getFb());
        }
    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            final IstkostenCreateDTO dto = new IstkostenCreateDTO("1124101", new BigDecimal(2026), new BigDecimal(1), new BigDecimal(5000));

            final Istkosten entity = istkostenMapper.toEntity(dto);

            assertThat(entity).isNotNull();
            assertThat(entity.getId().getJahr().compareTo(dto.jahr()));
            assertThat(entity.getId().getMonat().compareTo(dto.monat()));
            assertThat(entity.getIstkosten().compareTo(dto.istkosten()));
            assertThat(entity.getProjekt()).isNotNull();
            assertThat(entity.getProjekt().getProjnr()).isEqualTo(dto.projnr());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            final IstkostenUpdateDTO dto = new IstkostenUpdateDTO(new BigDecimal(11000));

            final Istkosten entity = istkostenMapper.toEntity(dto);

            assertThat(entity).isNotNull();
            assertThat(entity.getId()).isNull();
            assertThat(entity.getProjekt()).isNull();
            assertThat(entity.getIstkosten().compareTo(dto.istkosten()));
        }
    }
}
