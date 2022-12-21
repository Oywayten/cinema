package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

/**
 * Хранилище купленных билетов.
 * Created by Oywayten on 19.12.2022.
 */
public interface TicketRepository {

    /**
     * Метод для добавления купленного билета {@link Ticket} в хранилище.
     *
     * @param ticket пользователь типа Ticket.
     * @return {@link Optional} содержащий {@link Ticket} или null.
     */
    Optional<Ticket> add(Ticket ticket);
}
