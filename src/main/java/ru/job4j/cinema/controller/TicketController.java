package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

import java.util.Optional;

import static ru.job4j.cinema.util.Util.setUser;

/**
 * Контроллер купленных билетов.
 * Created by Oywayten on 21.12.2022.
 */
@Controller
public class TicketController {
    /**
     * Сервис купленных билетов {@link TicketService}.
     */
    private final TicketService ticketService;

    /**
     * Конструктор принимает сервис купленных {@link TicketService} билетов и инициализирует ими переменную {@link #ticketService}.
     *
     * @param ticketService Сервис купленных билетов {@link TicketService} для работы контроллера.
     */
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     *
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/getTicket")
    public String getTicket(Model model, HttpSession session) {
        setUser(model, session);
        return "sessions_view";
    }

    /**
     * Метод добавляет купленный билет {@link Ticket} в базу, после нажатия пользователем кнопки "Купить".
     *
     * @param redirectAttributes {@link RedirectAttributes}, специализация интерфейса {@link Model}
     *                           для передачи атрибутов.
     * @param ticket             Выбранный билет {@link Ticket} для покупки.
     * @return Строку редиректа на страницу подтверждения покупки.
     */
    @PostMapping("/buyTicket")
    public String buyTicket(final RedirectAttributes redirectAttributes,
                            HttpSession session, @ModelAttribute Ticket ticket) {
        Optional<Ticket> optionalTicket = ticketService.add(ticket);
        if (optionalTicket.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "message", "К сожалению билет уже куплен. Попробуйте выбрать другой билет"
        // TODO: 27.12.2022 Прописать на представлении session, как это сделано на addUser.htmo dream_job
            );
            return "redirect:/selectSessions";
        }
        return "redirect:/youTicket";
    }
}
