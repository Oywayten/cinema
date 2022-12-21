package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.sql.ResultSet;
import java.util.Optional;

/**
 * Хранилище пользователей.
 * Created by Oywayten on 08.11.2022.
 */
public interface UserRepository {

    /**
     * Метод для создания пользователя из
     * результатов запроса к хранилищу пользователей.
     *
     * @param it результаты запроса ResultSet.
     * @return созданный пользователь User.
     * @throws Exception ошибка при создании.
     */
    User createUser(ResultSet it) throws Exception;

    /**
     * Метод для добавления пользователя User в хранилище.
     *
     * @param user пользователь типа User.
     * @return Optional содержащий User или null.
     */
    Optional<User> add(User user);

    /**
     * Метод для поиска пользователя по почте или телефону.
     *
     * @param email почта для поиска String.
     * @param phone телефон для поиска String.
     * @return Optional содержащий User или null.
     */
    Optional<User> findUserByEmailOrPhone(String email, String phone);
}
