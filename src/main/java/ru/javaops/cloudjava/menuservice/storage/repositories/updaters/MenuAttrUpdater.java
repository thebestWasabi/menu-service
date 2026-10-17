package ru.javaops.cloudjava.menuservice.storage.repositories.updaters;

import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.AllArgsConstructor;
import ru.javaops.cloudjava.menuservice.dto.UpdateMenuRequest;
import ru.javaops.cloudjava.menuservice.storage.model.MenuItem;

import java.util.function.Function;

@AllArgsConstructor
public class MenuAttrUpdater<V> {
  private SingularAttribute<MenuItem, V> attribute;
  private Function<UpdateMenuRequest, V> function;

  public void updateAttr(CriteriaUpdate<MenuItem> criteria, UpdateMenuRequest dto) {
    //  TODO  criteria.set(attr, dtoValue);
    V value = function.apply(dto);
    if (value != null) {
      criteria.set(attribute, function.apply(dto));
    }
  }
}
