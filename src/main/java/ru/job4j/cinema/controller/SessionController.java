package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.service.SessionService;

import static ru.job4j.cinema.util.Util.setUser;

/**
 * Контроллер сеансов.
 * Created by Oywayten on 20.12.2022.
 */
@Controller
public class SessionController {
    /**
     * Сервис сеансов {@link SessionService}.
     */
    private final SessionService sessionService;

    /**
     * Конструктор принимает сервис сеансов {@link SessionService} и инициализирует ими переменную {@link #sessionService}.
     *
     * @param sessionService Сервис сеансов {@link SessionService} для работы контроллера.
     */
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * При переходе пользователя на конечную точку "/session", метод по GET-запросу возвращает представления с выводом
     * всех сеансов. Сохраняет информацию о пользователе из сессии и передает в представление список сеансов.
     *
     * @param model   {@link Model} для передачи списка сеансов и информации из сессии в представление.
     * @param session {@link HttpSession} сессия пользователя для получения пользователя и передачи в представление.
     * @return наименование представления для {@link org.springframework.web.servlet.ViewResolver}.
     */
    @GetMapping("/sessions")
    public String sessions(Model model, HttpSession session) {
        model.addAttribute("sessions", sessionService.findAll());
        setUser(model, session);
        return "sessions";
    }
}
