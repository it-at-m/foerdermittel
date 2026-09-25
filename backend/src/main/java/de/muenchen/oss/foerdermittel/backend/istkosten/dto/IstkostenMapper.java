package de.muenchen.oss.foerdermittel.backend.istkosten.dto;

import de.muenchen.oss.foerdermittel.backend.istkosten.Istkosten;
import de.muenchen.oss.foerdermittel.backend.istkosten.IstkostenPrimaryKey;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface IstkostenMapper {

    @Mapping(source = "id.projnr", target = "projnr")
    @Mapping(source = "id.jahr", target = "jahr")
    @Mapping(source = "id.monat", target = "monat")
    @Mapping(source = "id", target = "id", qualifiedByName = "buildIdString")
    @Mapping(source = "projekt.pname", target = "pname")
    @Mapping(source = "projekt.pstrasse", target = "pstrasse")
    @Mapping(source = "projekt.foerderbereich.fb", target = "fob_fb")
    IstkostenResponseDTO toDTO(Istkosten istkosten);

    @Mapping(source = "projnr", target = "id.projnr")
    @Mapping(source = "monat", target = "id.monat")
    @Mapping(source = "jahr", target = "id.jahr")
    @Mapping(source = "projnr", target = "projekt.projnr")
    Istkosten toEntity(IstkostenCreateDTO istkostenCreateDTO);

    @Mapping(target = "projekt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Istkosten toEntity(IstkostenUpdateDTO istkostenUpdateDTO);

    @Named("stringToPrimaryKey")
    default IstkostenPrimaryKey mapStringToPrimaryKey(final String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        final String[] parts = id.split("-");

        final String projnr = parts[0];
        final BigDecimal jahr = new BigDecimal(parts[1]);
        final BigDecimal monat = new BigDecimal(parts[2]);
        return new IstkostenPrimaryKey(projnr, jahr, monat);
    }

    @Named("buildIdString")
    default String buildIdString(final IstkostenPrimaryKey id) {
        if (id == null) {
            return "";
        }
        final String projnr = id.getProjnr();
        final BigDecimal jahr = id.getJahr();
        final BigDecimal monat = id.getMonat();

        if (projnr == null || jahr == null || monat == null) {
            return "";
        }

        return String.format("%s-%s-%s", projnr, jahr.toPlainString(), monat.toPlainString());
    }

}
