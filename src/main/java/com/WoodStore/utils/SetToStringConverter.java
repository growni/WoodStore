package com.WoodStore.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class SetToStringConverter implements AttributeConverter<Set<String>, String> {

    private final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbEmails) {
        if (dbEmails == null || dbEmails.isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(dbEmails.split(SEPARATOR))
                .collect(Collectors.toSet());
    }
}
