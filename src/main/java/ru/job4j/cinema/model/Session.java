package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных сеанса.
 * Created by Oywayten on 06.11.2022.
 */
public class Session {
    /**
     * Идентификатор сеанса
     */
    private int id;
    /**
     * Название фильма
     */
    private String name;

    /**
     * Зал сеанса
     */
    private Hall hall;

    /**
     * Конструктор без параметров.
     */
    public Session() {
    }

    /**
     * Конструктор принимает все поля в качестве входных параметров и устанавливает их в поля.
     */
    public Session(int id, String name, Hall hall) {
        this.id = id;
        this.name = name;
        this.hall = hall;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return id == session.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
