package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cinema.model.Session;
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
     * Вывод сеансов.
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
    // TODO: 28.12.2022 Дописать тут выбор сеанса и отправку, потом редирект на выбор места (вью и метод). Далее с вью выбора места - POST отправка.

    @GetMapping("/photoSession/{sessionId}")
    public ResponseEntity<Resource> download(@PathVariable("sessionId") Integer sessionId) {
        Session session = sessionService.findById(sessionId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(session.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(session.getPhoto()));
    }

}
