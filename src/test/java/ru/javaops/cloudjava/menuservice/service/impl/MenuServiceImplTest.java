package ru.javaops.cloudjava.menuservice.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javaops.cloudjava.menuservice.BaseTest;
import ru.javaops.cloudjava.menuservice.dto.CreateMenuRequest;
import ru.javaops.cloudjava.menuservice.dto.MenuItemDto;
import ru.javaops.cloudjava.menuservice.dto.SortBy;
import ru.javaops.cloudjava.menuservice.dto.UpdateMenuRequest;
import ru.javaops.cloudjava.menuservice.exception.MenuServiceException;
import ru.javaops.cloudjava.menuservice.service.MenuService;
import ru.javaops.cloudjava.menuservice.storage.model.Category;
import ru.javaops.cloudjava.menuservice.storage.repositories.MenuItemRepository;
import ru.javaops.cloudjava.menuservice.testutils.TestData;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MenuServiceImplTest extends BaseTest {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuItemRepository repository;

    @Test
    void getMenu_returnsCorrectMenu_ifMenuExistsInDB() {
        final var id = getIdByName("Tea");
        final var menuItem = menuService.getMenu(id);
        assertThat(menuItem).isNotNull();
        assertThat(menuItem.getId()).isEqualTo(id);
        assertThat(menuItem.getName()).isEqualTo("Tea");
        assertThat(menuItem.getCreatedAt()).isNotNull();
        assertThat(menuItem.getUpdatedAt()).isNotNull();
    }

    @Test
    void getMenu_throwException_ifMenuDoesNotExistInDB() {
        assertThrows(MenuServiceException.class, () -> menuService.getMenu(101L));
    }

    @Test
    void getMenusFor_DRINKS_returnsCorrectList() {
        List<MenuItemDto> drinks = menuService.getMenusFor(Category.DRINKS, SortBy.AZ);
        assertThat(drinks).hasSize(3);
        assertElementsInOrder(drinks, MenuItemDto::getName, List.of("Cappuccino", "Tea", "Wine"));
    }

    @Test
    void createMenuItem_createsMenuItem() {
        var dto = TestData.createMenuRequest();
        // Вычитаем некоторое количество наносекунд из-за возможных проблем со сравнением дат (проявляется на Windows,
        // при тестировании на Ubuntu и Mac такой проблемы не возникало)
        // так как Postgres не поддерживает точность дат до наносекунд из коробки
        var now = LocalDateTime.now().minusNanos(1000);
        MenuItemDto result = menuService.createMenuItem(dto);
        assertThat(result.getId()).isNotNull();
        assertFieldsEquality(result, dto, "name", "description", "price", "imageUrl", "timeToCook");
        assertThat(result.getCreatedAt()).isAfter(now);
        assertThat(result.getUpdatedAt()).isAfter(now);
    }

    @Test
    void createMenuItem_throwException_ifMenuItemNameAlreadyExistsInDB() {
        assertThrows(MenuServiceException.class, () ->
                menuService.createMenuItem(CreateMenuRequest.builder().name("Cappuccino").build()));
    }

    @Test
    void updateMenuItem_updatesMenuItem_ifMenuItemExistsInDB() {
        final var id = getIdByName("Cappuccino");
        final var menuItem = TestData.updateMenuFullRequest();
        final var updatedMenuItem = menuService.updateMenuItem(id, menuItem);

        assertFieldsEquality(updatedMenuItem, menuItem, "name", "description", "price", "imageUrl", "timeToCook");
    }

    @Test
    void updateMenuItem_throwException_ifMenuItemDoesNotExistInDB() {
        final var idNotExisting = 101L;
        final UpdateMenuRequest menuItem = TestData.updateMenuFullRequest();

        assertThrows(MenuServiceException.class, () -> menuService.updateMenuItem(idNotExisting, menuItem));
    }

    @Test
    void updateMenuItem_throwException_ifMenuItemNameAlreadyExistsInDB() {
        final Long id = getIdByName("Tea");
        final UpdateMenuRequest menuItem = TestData.updateMenuFullRequest();
        menuItem.setName("Cappuccino");

        assertThrows(MenuServiceException.class, () -> menuService.updateMenuItem(id, menuItem));
    }

    @Test
    void deleteMenuItem_deletedMenuItem() {
        final var id = getIdByName("Tea");
        menuService.deleteMenuItem(id);
        final var menuOptional = repository.findById(id);
        assertThat(menuOptional).isEmpty();
    }
}
