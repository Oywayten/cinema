package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserRepository;

import java.util.Optional;

/**
 * Сервис пользователей.
 * Created by Oywayten on 19.12.2022.
 */
@Service
public class UserService {
    /**
     * Подключенный репозиторий пользователей.
     */
    public UserRepository repository;

    /**
     * Конструктор принимает репозиторий пользователей и инициализирует им переменную {@link #repository}.
     *
     * @param repository репозиторий пользователей для работы сервиса.
     */
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Добавляет пользователя в репозиторий.
     *
     * @param user переданный пользователь {@link User}
     * @return {@link Optional} содержащий {@link User} или null.
     */
    public Optional<User> add(User user) {
        return repository.add(user);
    }

    /**
     * Принимает почту и ищет в хранилище пользователя, у которого совпадает почта.
     * Возвращает {@link Optional} с первым же найденным в хранилище пользователем, у которого совпала почта,
     * или с null.
     *
     * @param emailOrPhone {@link String} почта для поиска в хранилище.
     * @param password     {@link String} пароль для поиска в хранилище.
     * @return {@link Optional} содержащий {@link User} или null.
     */
    public Optional<User> findUserByEmailorPhonAndPassword(String emailOrPhone, String password) {
        return repository.findUserByEmailorPhoneAndPassword(emailOrPhone, password);
    }
}
