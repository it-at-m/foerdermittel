package de.muenchen.oss.foerdermittel.backend.istkosten.dto;

import de.muenchen.oss.foerdermittel.backend.istkosten.Istkosten;
import de.muenchen.oss.foerdermittel.backend.istkosten.IstkostenPrimaryKey;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

        final int lastDashIndex = id.lastIndexOf('-');
        final int secondLastDashIndex = lastDashIndex > 0 ? id.lastIndexOf('-', lastDashIndex - 1) : -1;

        if (secondLastDashIndex <= 0) {
            throwInvalidId(id);
        }

        try {
            final BigDecimal jahr = parseYear(id, secondLastDashIndex, lastDashIndex);
            final BigDecimal monat = parseMonth(id, lastDashIndex);
            validateYearAndMonth(jahr, monat, id);
            return new IstkostenPrimaryKey(id.substring(0, secondLastDashIndex), jahr, monat);
        } catch (NumberFormatException e) {
            throwInvalidId(id);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
    }

    private BigDecimal parseYear(final String id, final int start, final int end) {
        return new BigDecimal(id.substring(start + 1, end));
    }

    private BigDecimal parseMonth(final String id, final int end) {
        return new BigDecimal(id.substring(end + 1));
    }

    private void validateYearAndMonth(final BigDecimal jahr, final BigDecimal monat, final String id) {
        if (jahr.scale() > 0 || monat.scale() > 0
                || jahr.compareTo(BigDecimal.valueOf(1970)) < 0
                || jahr.compareTo(BigDecimal.valueOf(2100)) > 0
                || monat.compareTo(BigDecimal.ONE) < 0
                || monat.compareTo(BigDecimal.valueOf(12)) > 0) {
            throwInvalidId(id);
        }
    }

    private void throwInvalidId(final String id) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Istkosten id: " + id);
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
