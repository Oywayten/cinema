package ru.job4j.cinema.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

import java.util.Optional;

/**
 * Контроллер купленных билетов.
 * Created by Oywayten on 21.12.2022.
 */
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
     * Метод добавляет купленный билет {@link Ticket} в базу, после нажатия пользователем кнопки "Купить".
     *
     * @param model  Модель {@link Model} для передачи атрибутов.
     * @param ticket Выбранный билет {@link Ticket} для покупки.
     * @return Строку редиректа на страницу подтверждения покупки.
     */
    @PostMapping("/buyTicket")
    public String buyTicket(Model model, @ModelAttribute Ticket ticket) {
        Optional<Ticket> optionalTicket = ticketService.add(ticket);
        if (optionalTicket.isEmpty()) {
            model.addAttribute("message", "К сожалению билет уже куплен. Попробуйте выбрать другой билет");
            return "redirect:/selectSessions";
        }
        return "redirect:/youTicket";
    }
}
