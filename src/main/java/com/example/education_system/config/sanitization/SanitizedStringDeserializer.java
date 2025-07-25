package com.example.education_system.config.sanitization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class SanitizedStringDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser p, DeserializationContext context) throws IOException {
        String raw = p.getText();
        return HtmlSanitizerService.sanitize(raw);
    }
}
