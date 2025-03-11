package ru.javaops.cloudjava.menuservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.cloudjava.menuservice.dto.CreateMenuRequest;
import ru.javaops.cloudjava.menuservice.dto.MenuItemDto;
import ru.javaops.cloudjava.menuservice.dto.SortBy;
import ru.javaops.cloudjava.menuservice.dto.UpdateMenuRequest;
import ru.javaops.cloudjava.menuservice.service.MenuService;
import ru.javaops.cloudjava.menuservice.storage.model.Category;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuService menuService;

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDto> getMenuItem(@PathVariable("id") final Long id) {
        log.info("Received GET request to get MenuItem: id={}", id);
        return ResponseEntity.ok(menuService.getMenu(id));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDto>> getAllMenuItems(
            @RequestParam(value = "category") final String category,
            @RequestParam(value = "sort", defaultValue = "az") final String sortBy
    ) {
        log.info("Received request to GET list of MenuItems for category={}, sorted by={}", category, sortBy);
        return ResponseEntity.ok(menuService.getMenusFor(Category.fromString(category), SortBy.fromString(sortBy)));
    }

    @PostMapping
    public ResponseEntity<MenuItemDto> createMenuItem(@RequestBody final CreateMenuRequest create) {
        log.info("Received POST request to create MenuItem: {}", create);
        final var createdMenuItemDto = menuService.createMenuItem(create);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(createdMenuItemDto.getId())
                        .toUri())
                .body(createdMenuItemDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable("id") final Long id) {
        log.info("Received DELETE request to delete MenuItem: id={}", id);
        menuService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateMenuItem(@PathVariable("id") final Long id, @RequestBody final UpdateMenuRequest update) {
        log.info("Received PATCH request to update MenuItem: id={}. MenuItem = {}", id, update);
        return ResponseEntity.ok(menuService.updateMenuItem(id, update));
    }
}
