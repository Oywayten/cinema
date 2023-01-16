package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

/**
 * Класс для работы с данными базы купленных билетов tickets.
 * Created by Oywayten on 19.12.2022.
 */
@Repository
public class JdbcTicketRepository implements TicketRepository {
    /**
     * Строка запроса к БД для добавления билета
     */
    private static final String ADD = """
            INSERT INTO
            tickets (session_id, pos_row, cell, user_id)
            VALUES (?, ?, ?, ?)
            """;
    /**
     * Логгер для логирования
     */
    private static final Logger LOG =
            LogManager.getLogger(JdbcTicketRepository.class);
    /**
     * Пул подключений к БД
     */
    private final BasicDataSource pool;

    /**
     * Конструктор принимает пул подключений к БД и инициализирует ими поле {@link #pool}.
     *
     * @param pool пул подключений к БД.
     */
    public JdbcTicketRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Добавляет купленный билет в хранилище.
     *
     * @param ticket билет для внесения в хранилище.
     * @return {@link Optional} содержащий {@link Ticket} или null.
     */
    @Override
    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> optionalTicket = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(ADD,
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                    optionalTicket = Optional.of(ticket);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return optionalTicket;
    }
}
