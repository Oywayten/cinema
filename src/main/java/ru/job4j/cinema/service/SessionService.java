package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.repository.SessionRepository;

import java.util.List;

/**
 * Сервис сеансов.
 * Created by Oywayten on 19.12.2022.
 */
@Service
public class SessionService {
    /**
     * Подключенный репозиторий сеансов.
     */
    public SessionRepository repository;

    /**
     * Конструктор принимает репозиторий сеансов и инициализирует им переменную {@link #repository}.
     *
     * @param repository репозиторий сеансов для работы сервиса.
     */
    public SessionService(SessionRepository repository) {
        this.repository = repository;
    }

    /**
     * Получает все сеансы из подключенного репозитория.
     *
     * @return список сеансов {@link List<Session>}.
     */
    public List<Session> findAll() {
        return repository.findAll();
    }
}
