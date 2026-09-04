package de.muenchen.oss.foerdermittel.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.module.SimpleModule;

@Configuration
public class JacksonConfiguration {

    @Bean
    public JacksonModule trimmingModule() {
        final SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new TrimStringDeserializer());
        return module;
    }

    /// This deserializer trims leading and trailing whitespaces and normalizes whitespaces in between
    /// characters to singular whitespaces. Additionally, whitespace before and after newline
    /// are removed.
    /* package */ static class TrimStringDeserializer extends ValueDeserializer<String> {
        @Override
        public String deserialize(final JsonParser p, final DeserializationContext ctx) {
            if (p.currentToken() != JsonToken.VALUE_STRING) {
                return (String) ctx.handleUnexpectedToken(String.class, p);
            }
            final String value = p.getValueAsString();
            return value == null ? null
                    : value.replaceAll("[ \\t]+\\n", "\n")
                            .replaceAll("\\n[ \\t]+", "\n")
                            .trim()
                            .replaceAll(" +", " ");
        }
    }

}
