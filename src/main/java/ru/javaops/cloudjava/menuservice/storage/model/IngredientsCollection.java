package ru.javaops.cloudjava.menuservice.storage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsCollection {
  private List<Ingredient> ingredients;
}
