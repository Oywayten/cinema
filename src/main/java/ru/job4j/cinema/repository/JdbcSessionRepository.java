package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с данными базы sessions.
 * Created by Oywayten on 19.12.2022.
 */
@Repository
public class JdbcSessionRepository implements SessionRepository {
    /**
     * Строка запроса для получения всех сеансов из хранилища сеансов
     */
    private static final String FIND_ALL = """
             select s.id as s_id, s.name as s_name, s.foto as s_foto,
             h.id as h_id, h.name h_name, rows, cells
             from sessions as s join halls as h
             on s.halls_id = h.id
            """;
    private static final String FIND_BY_ID = FIND_ALL + " WHERE s.id = ?";
    /**
     * Логгер для логирования
     */
    private static final Logger LOG =
            LogManager.getLogger(JdbcSessionRepository.class);
    /**
     * Пул подключений к БД
     */
    private final BasicDataSource pool;

    /**
     * Конструктор принимает пул подключений к
     * БД и инициализирует ими поле {@link #pool}.
     *
     * @param pool пул подключений к БД.
     */
    public JdbcSessionRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Метод для получения списка всех сеансов из хранилища.
     *
     * @return List<Session> - список сеансов.
     */
    @Override
    public List<Session> findAll() {
        List<Session> session = new ArrayList<>();
        try (Connection cn = pool.getConnection(); PreparedStatement ps = cn.prepareStatement(FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    session.add(createSession(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return session;
    }

    /**
     * Метод создает сеанс из переданных результатов запроса к хранилищу.
     *
     * @param it набор результатов из хранилища типа {@link ResultSet}.
     * @return Session - созданный сеанс.
     * @throws SQLException при неудачной работе с хранилищем.
     */
    @Override
    public Session createSession(ResultSet it) throws SQLException {
        return new Session(
                it.getInt("s_id"),
                it.getString("s_name"),
                it.getBytes("s_foto"),
                new Hall(it.getInt("h_id"),
                        it.getString("h_name"),
                        it.getInt("rows"),
                        it.getInt("cells"))
        );
    }

    /**
     * Возвращает сеанс по его идентификатору.
     *
     * @param id идентификатор сеанса int.
     * @return Session из базы по нужному id.
     */
    public Session findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createSession(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
