package com.sakila.converter;

import com.sakila.enums.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : Arrays.stream(Rating.values()).filter(rating -> rating.getCode().equalsIgnoreCase(dbData)).findFirst().orElseThrow();
    }
}
