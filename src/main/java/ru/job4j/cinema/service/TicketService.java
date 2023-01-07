package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Optional;

/**
 * Сервис хранилища билетов.
 * Created by Oywayten on 19.12.2022.
 */
@Service
public class TicketService {
    /**
     * Репозиторий купленных билетов.
     */
    private final TicketRepository repository;

    /**
     * Конструктор принимает репозиторий купленных билетов и инициализирует
     * им переменную {@link #repository}.
     *
     * @param repository репозиторий купленных билетов для работы сервиса.
     */
    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    /**
     * Добавляет переданный билет в репозиторий.
     *
     * @param ticket переданный билет {@link Ticket}
     * @return {@link Optional} содержащий {@link Ticket} или null.
     */
    public Optional<Ticket> add(Ticket ticket) {
        return repository.add(ticket);
    }
}


