package com.sakila.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.sakila.dto.CategoryDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Category {
    ACTION(1, "Action"),
    ANIMATION(2, "Animation"),
    CHILDREN(3, "Children"),
    CLASSICS(4, "Classics"),
    COMEDY(5, "Comedy"),
    DOCUMENTARY(6, "Documentary"),
    DRAMA(7, "Drama"),
    FAMILY(8, "Family"),
    FOREIGN(9, "Foreign"),
    GAMES(10, "Games"),
    HORROR(11, "Horror"),
    MUSIC(12, "Music"),
    NEW(13, "New"),
    SCI_FI(14, "Sci-Fi"),
    SPORTS(15, "Sports"),
    TRAVEL(6, "Travel");;
    private final int id;
    @JsonValue
    private final String name;

    @JsonCreator
    public static Category from(String name) {
        return Arrays.stream(Category.values())
                .filter(category -> category.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Category from(int id) {
        return Arrays.stream(Category.values())
                .filter(category -> category.getId() == id).findFirst().orElse(null);
    }

    public CategoryDto toDto() {
        return CategoryDto.builder()
                .categoryId(id)
                .name(name)
                .build();
    }

    @Override
    public String toString() {
        return name;
    }
}
