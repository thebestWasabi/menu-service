package ru.javaops.cloudjava.menuservice.storage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    private String name;
    private int calories;

}
