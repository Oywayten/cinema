package ru.job4j.cinema.controller;

import ru.job4j.cinema.service.UserService;

/**
 * Контроллер пользователей.
 * Created by Oywayten on 21.12.2022.
 */
public class UserController {
    /**
     * Сервис пользователей.
     */
    private final UserService userService;

    /**
     * Конструктор принимает сервис пользователей и инициализирует им переменную {@link #userService}.
     * @param userService сервис пользователей {@link UserService} для работы контроллера.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
