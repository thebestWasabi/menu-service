package ru.javaops.cloudjava.menuservice.storage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCollection {
    private List<Ingredient> ingredients;
}
