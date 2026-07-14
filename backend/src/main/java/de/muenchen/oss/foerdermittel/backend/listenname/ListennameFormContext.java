package de.muenchen.oss.foerdermittel.backend.listenname;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ListennameFormContext(@NotNull List<String> listennamen) {
    public ListennameFormContext {
        listennamen = List.copyOf(listennamen);
    }
}
