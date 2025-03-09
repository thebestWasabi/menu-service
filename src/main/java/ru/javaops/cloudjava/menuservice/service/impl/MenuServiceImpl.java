package ru.javaops.cloudjava.menuservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.javaops.cloudjava.menuservice.dto.CreateMenuRequest;
import ru.javaops.cloudjava.menuservice.dto.MenuItemDto;
import ru.javaops.cloudjava.menuservice.dto.SortBy;
import ru.javaops.cloudjava.menuservice.dto.UpdateMenuRequest;
import ru.javaops.cloudjava.menuservice.exception.MenuServiceException;
import ru.javaops.cloudjava.menuservice.mapper.MenuItemMapper;
import ru.javaops.cloudjava.menuservice.service.MenuService;
import ru.javaops.cloudjava.menuservice.storage.model.Category;
import ru.javaops.cloudjava.menuservice.storage.model.MenuItem;
import ru.javaops.cloudjava.menuservice.storage.repositories.MenuItemRepository;

import java.util.List;

/**
 * @author Maxim Khamzin
 * @link <a href="https://mkcoder.net">mkcoder.net</a>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    @Override
    public MenuItemDto createMenuItem(final CreateMenuRequest dto) {
        final MenuItem menuItem = menuItemMapper.toDomain(dto);
        try {
            return menuItemMapper.toDto(menuItemRepository.save(menuItem));
        }
        catch (DataIntegrityViolationException e) {
            throw new MenuServiceException("Failed to create MenuItem: %s".formatted(dto), HttpStatus.CONFLICT);
        }
    }

    @Override
    public void deleteMenuItem(final Long id) {
        menuItemRepository.deleteById(id);
    }

    @Override
    public MenuItemDto updateMenuItem(final Long id, final UpdateMenuRequest update) {
        try {
            final int count = menuItemRepository.updateMenu(id, update);
            if (count == 0) {
                throw new MenuServiceException("MenuItem not found: id=%d".formatted(id), HttpStatus.NOT_FOUND);
            }
            return getMenu(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new MenuServiceException("Failed to update MenuItem: id=%d".formatted(id), HttpStatus.CONFLICT);
        }
    }

    @Override
    public MenuItemDto getMenu(final Long id) {
        final var menuItemOptional = menuItemRepository.findById(id);
        if (menuItemOptional.isEmpty())
            throw new MenuServiceException("MenuItem not found: id=%d".formatted(id), HttpStatus.NOT_FOUND);

        return menuItemMapper.toDto(menuItemOptional.get());
    }

    @Override
    public List<MenuItemDto> getMenusFor(final Category category, final SortBy sortBy) {
        return menuItemMapper.toDtoList(menuItemRepository.getMenusFor(category, sortBy));
    }
}
