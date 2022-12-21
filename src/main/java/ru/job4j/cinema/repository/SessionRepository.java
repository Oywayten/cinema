package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Session;

import java.sql.ResultSet;
import java.util.List;

/**
 * Хранилище сеансов.
 * Created by Oywayten on 19.12.2022.
 */
public interface SessionRepository {

    /**
     * Метод для получения списка всех сеансов.
     * @return спиосок сеансов List<Session>.
     */
    List<Session> findAll();

    /**
     * Метод для создания сеанса из
     * результатов запроса к хранилищу сеансов.
     * @param it результаты запроса ResultSet.
     * @return созданный сеанс Session.
     * @throws Exception ошибка при создании.
     */
    Session createSession(ResultSet it) throws Exception;
}
