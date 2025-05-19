package com.sakila.converter;

import com.sakila.enums.Language;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter
public class LanguageConverter implements AttributeConverter<Language, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Language attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public Language convertToEntityAttribute(Integer dbData) {
        return (dbData == null) ? null : Arrays.stream(Language.values()).filter(language -> language.getId() == dbData).findFirst().orElseThrow();
    }

}
