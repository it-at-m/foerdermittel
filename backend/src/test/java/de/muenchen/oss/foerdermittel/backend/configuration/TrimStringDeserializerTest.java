package de.muenchen.oss.foerdermittel.backend.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

class TrimStringDeserializerTest {

    private final JsonMapper jsonMapper = JsonMapper.builder().addModule(trimmingModule()).build();

    @Test
    void givenLeadingAndTrailingWhitespace_thenTrimWhitespace() {
        final String result = jsonMapper.readValue("\"   hello world   \"", String.class);
        assertEquals("hello world", result);
    }

    @Test
    void givenMultipleSpacesBetweenWords_thenNormalizeToSingleSpaces() {
        final String result = jsonMapper.readValue("\"hello     world\"", String.class);
        assertEquals("hello world", result);
    }

    @Test
    void givenWhitespaceBeforeNewline_thenRemoveWhitespaceBeforeNewline() {
        final String result = jsonMapper.readValue("\"hello     \\nworld\"", String.class);
        assertEquals("hello\nworld", result);
    }

    @Test
    void givenWhitespaceAfterNewline_thenRemoveWhitespaceAfterNewline() {
        final String result = jsonMapper.readValue("\"hello\\n     world\"", String.class);
        assertEquals("hello\nworld", result);
    }

    @Test
    void givenWhitespaceAroundNewlines_thenRemoveWhitespaceAroundNewlines() {
        final String result = jsonMapper.readValue("\"  hello   \\n     world   \\n   again  \"", String.class);
        assertEquals("hello\nworld\nagain", result);
    }

    @Test
    void givenSingleNewlines_thenPreserveNewlines() {
        final String result = jsonMapper.readValue("\"hello\\nworld\"", String.class);
        assertEquals("hello\nworld", result);
    }

    @Test
    void givenNullValue_thenReturnNull() {
        final String result = jsonMapper.readValue("null", String.class);
        assertNull(result);
    }

    @Test
    void givenOnlyWhitespace_thenReturnEmptyString() {
        final String result = jsonMapper.readValue("\"     \"", String.class);
        assertEquals("", result);
    }

    private SimpleModule trimmingModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new JacksonConfiguration.TrimStringDeserializer());
        return module;
    }

}
