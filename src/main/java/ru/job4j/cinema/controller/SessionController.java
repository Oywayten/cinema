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
import ru.job4j.cinema.model.Ticket;
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
    @GetMapping({"/index", "/sessions"})
    public String sessions(final Model model, final HttpSession session) {
        model.addAttribute("sessions", sessionService.findAll());
        setUser(model, session);
        return "sessions_view";
    }


    /**
     * Сюда попадаем с вьюшки sessions_view.
     * Метод передает выбранный сеанс и параметры сессии
     * в представление выбора ряда и места.
     * Возвращает представление с выбором места на выбранный сеанс.
     *
     * @param model       {@link Model} для передачи списка сеансов и
     *                    информации из сессии в представление.
     * @param id          идентификатор сеанса типа int.
     * @param httpSession {@link HttpSession} сессия пользователя для получения
     *                    пользователя и передачи в представление.
     * @return {@link String} наименования
     * представления выбора ряда и места для отображение пользователю.
     */
    @GetMapping("/session1/{sessionId}")
    public String selectPlace(
            Model model,
            @PathVariable("sessionId") int id,
            HttpSession httpSession) {
        model.addAttribute("session1", sessionService.findById(id));
        setUser(model, httpSession);
        return "places_view";
    }

    /**
     * Метод отображает данные нужного билета пользователю: сеанс, ряд и место.
     * Выводит сводную информацию пользователю для окончательного
     * подтверждения покупки
     * <p>
     * Сюда попадаем с вьюшки places_view.
     * Метод принимает id сеанса, ряд и место, и параметры сессии.
     * Возвращает представление со сводкой по билету.
     *
     * @param model       {@link Model} для передачи списка сеансов и
     *                    информации из сессии в представление.
     * @param sessionId   идентификатор сеанса типа int.
     * @param row         номер выбранного ряда int.
     * @param cell        номер выбранного места int.
     * @param httpSession {@link HttpSession} сессия пользователя для получения
     *                    пользователя и передачи в представление.
     * @return {@link String} наименования
     * представления для выдачи пользователю.
     */
    @GetMapping("/session2/{sessionId}/{row}/{cell}")
    public String summary(
            Model model,
            @PathVariable("sessionId") int sessionId,
            @PathVariable("row") int row,
            @PathVariable("cell") int cell,
            HttpSession httpSession) {
        model.addAttribute("session1", sessionService.findById(sessionId));
        model.addAttribute("row", row);
        model.addAttribute("cell", cell);
        setUser(model, httpSession);
        return "summary_view";
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

    /**
     * Метод получает модель {@link Model}, сессию {@link HttpSession} и билет {@link Ticket},
     * который дополняет идентификатором пользователя из {@link HttpSession} и добавляет в модель
     * вместе с информацией о сеансе. Возвращает представление спасибной страницы.
     *
     * @param model       {@link Model} для передачи списка сеансов и
     *                    информации из сессии в представление.
     * @param ticket      билет {@link Ticket}.
     * @param httpSession {@link HttpSession} сессия пользователя для получения
     *                    пользователя и передачи в представление.
     * @return {@link String} наименования
     * представления для выдачи пользователю.
     */
    @GetMapping("/thankyou")
    public String thankyou(Model model, @ModelAttribute Ticket ticket, HttpSession httpSession) {
        final int sessionId = ticket.getSessionId();
        model.addAttribute("session1", sessionService.findById(sessionId));
        model.addAttribute("ticket", ticket);
        setUser(model, httpSession);
        return "thankyou_view";
    }
}
