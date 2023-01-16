package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.TicketService;

import java.util.Optional;

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
     * Конструктор принимает сервис купленных {@link TicketService} билетов
     * и инициализирует ими переменную {@link #ticketService}.
     *
     * @param ticketService Сервис купленных билетов {@link TicketService}
     *                      для работы контроллера.
     */
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Метод добавляет купленный билет {@link Ticket} в базу,
     * после нажатия пользователем кнопки "Купить".
     *
     * @param redirectAttributes {@link RedirectAttributes},
     *                           специализация интерфейса {@link Model}
     *                           для передачи атрибутов.
     * @param ticket             Выбранный билет {@link Ticket} для покупки.
     * @return Строку редиректа спасибной страницы.
     */
    @PostMapping("/buyTicket")
    public String buyTicket(@ModelAttribute Ticket ticket,
                            @RequestParam("id") int id,
                            HttpSession httpSession,
                            final RedirectAttributes redirectAttributes) {
        final User user = (User) httpSession.getAttribute("user");
        ticket.setUserId(user.getId());
        ticket.setSessionId(id);
        Optional<Ticket> optionalTicket = ticketService.add(ticket);
        if (optionalTicket.isEmpty()) {
            redirectAttributes.addFlashAttribute(
                    "message",
                    "К сожалению, кто-то оказался быстрее и купил билет на выбранные места. Попробуйте\n"
                    + "            выбрать другой билет."
            );
            return "redirect:/sessions";
        }
        redirectAttributes.addFlashAttribute("ticket", ticket);
        return "redirect:/thankyou";
    }
}
