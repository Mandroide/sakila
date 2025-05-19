package com.sakila.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Language {
    ENGLISH(1, "English"),
    ITALIAN(2, "Italian"),
    JAPANESE(3, "Japanese"),
    MANDARIN(4, "Mandarin"),
    FRENCH(5, "French"),
    GERMAN(6, "German");
    private final int id;
    @JsonValue
    private final String name;

    @JsonCreator
    public static Language from(String name) {
        return Arrays.stream(Language.values())
                .filter(language -> language.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return name;
    }
}
