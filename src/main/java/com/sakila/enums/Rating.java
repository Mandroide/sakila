package com.sakila.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Rating {
    G("G"), PG("PG"), PG13("PG-13"), R("R"), NC17("NC-17");
    @JsonValue
    private final String code;

    @Override
    public String toString() {
        return code;
    }

    @JsonCreator
    public static Rating from(String code) {
        return Arrays.stream(Rating.values())
                .filter(rating -> rating.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
    }
}
