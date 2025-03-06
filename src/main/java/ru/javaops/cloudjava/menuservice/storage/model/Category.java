package ru.javaops.cloudjava.menuservice.storage.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import ru.javaops.cloudjava.menuservice.exception.MenuServiceException;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */

public enum Category {
    BREAKFAST,
    LUNCH,
    DINNER,
    DRINKS,
    SNACKS,
    SALADS;

    @JsonCreator
    public static Category fromString(final String str) {
        Assert.hasText(str, "Category string must not be null or empty");

        try {
            return valueOf(str.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new MenuServiceException(
                    "Invalid category: %s. Available categories: BREAKFAST, LUNCH, DINNER, DRINKS, SNACKS, SALADS"
                            .formatted(str), HttpStatus.BAD_REQUEST);
        }
    }
}
