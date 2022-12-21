package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных пользователя.
 * Created by Oywayten on 06.11.2022.
 */
public class User {
    /**
     * Идентификатор пользователя
     */
    private int id;
    /**
     * Имя пользователя
     */
    private String name;
    /**
     * Пароль пользователя
     */
    private String password;
    /**
     * Емейл пользователя
     */
    private String email;
    /**
     * Телефон пользователя
     */
    private String phone;

    /**
     * Конструктор без параметров.
     */
    public User() {
    }

    /**
     * Конструктор принимает все поля в качестве входных параметров и устанавливает их в поля.
     */
    public User(int id, String name, String password, String email, String phone) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


