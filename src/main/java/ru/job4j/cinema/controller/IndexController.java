package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер главной страницы.
 * Created by Oywayten on 21.12.2022.
 */
@Controller
public class IndexController {

    /**
     * Метод делает недействительной сессию работы пользователя.
     *
     * @param session сессия пользователя.
     * @return строку с редиректом на представление для входа в систему.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}