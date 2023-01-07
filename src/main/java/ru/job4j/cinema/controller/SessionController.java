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
import org.springframework.web.bind.annotation.ModelAttribute;
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
     * Конструктор принимает сервис сеансов {@link SessionService} и
     * инициализирует ими переменную {@link #sessionService}.
     *
     * @param sessionService Сервис сеансов {@link SessionService}
     *                       для работы контроллера.
     */
    public SessionController(final SessionService sessionService) {
        // TODO: 05.01.2023 Если не работает, то убрать final тут и в других местах
        this.sessionService = sessionService;
    }

    /**
     * Метод вызывается на конечной точке /index и делает проверку пользователя.
     * Если не выполнен вход в систему, то пользователю назначается имя Гость.
     * Далее происходит вывод сеансов: метод по GET-запросу возвращает
     * представление с выводом всех сеансов.
     *
     * @param model   {@link Model} для передачи списка сеансов и
     *                информации из сессии в представление.
     * @param session {@link HttpSession} сессия пользователя для получения
     *                пользователя и передачи в представление.
     * @return наименование
     * представления для {@link org.springframework.web.servlet.ViewResolver}.
     */
    @GetMapping({"/index", "/session"})
    public String sessions(final Model model, final HttpSession session) {
        model.addAttribute("sessions", sessionService.findAll());
        setUser(model, session);
        return "sessions_view";
    }

    /**
     * Сюда попадаем с вьюшки session_view.
     * Метод передает выбранный сеанс и параметры сессии
     * в представление выбора ряда и места.
     * Возвращает redirect:/hall.
     *
     * @param model       {@link Model} для передачи списка сеансов и
     *                    информации из сессии в представление.
     * @param id          идентификатор сеанса типа int.
     * @param httpSession {@link HttpSession} сессия пользователя для получения
     *                    пользователя и передачи в представление.
     * @return {@link String} наименования
     * представления для выдачи пользователю.
     */
    @GetMapping("/session/sessionId")
    public String selectPlace(
            Model model,
            @PathVariable("sessionId") int id,
            HttpSession httpSession) {
        model.addAttribute("session", sessionService.findById(id));
        setUser(model, httpSession);
        return "redirect:/places_view";
    }

    /**
     * /cinema/godzilla
     * <p>
     * Сюда попадаем при редиректе на /places и
     * возвращает представление places_view со свободными и занятыми местами.
     * Во вьюшке проверяем, не равно ли место тому, что есть в базе,
     * иначе место другого цвета. Проверяем через равенство HashSet, чтобы поиск был быстрый
     * по хэшкоду.
     */
    @GetMapping("/places")
    public String places(@ModelAttribute Session session, final Model model, final HttpSession httpSession) {
        setUser(model, httpSession);
        model.addAttribute("hall", session.getHall());
        return "places_view";
    }

    /**
     * Метод для получения в представление постера сеанса из БД по id сеанса.
     *
     * @param sessionId id сеанса в базе для получения постера сеанса.
     * @return {@link ResponseEntity} с полученным постером.
     */
    @GetMapping("/photoSession/{sessionId}")
    public ResponseEntity<Resource> download(@PathVariable("sessionId") final Integer sessionId) {
        Session session = sessionService.findById(sessionId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(session.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(session.getPhoto()));
    }
}
