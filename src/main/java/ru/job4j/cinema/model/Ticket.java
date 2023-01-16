package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных купленного билета.
 * Created by Oywayten on 06.11.2022.
 */
public class Ticket {
    /**
     * Идентификатор купленного билета
     */
    private int id;
    /**
     * Идентификатор сессии
     */
    private int sessionId;
    /**
     * Номер ряда в зале
     */
    private int row;
    /**
     * Номер места в ряду
     */
    private int cell;
    /**
     * Идентификатор пользователя
     */
    private int userId;

    /**
     * Конструктор без параметров.
     */
    public Ticket() {
    }

    /**
     * Конструктор принимает все поля в качестве входных параметров и устанавливает их в поля.
     */
    public Ticket(int id, int sessionId, int posRow, int cell, int userId) {
        this.id = id;
        this.sessionId = sessionId;
        this.row = posRow;
        this.cell = cell;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return sessionId == ticket.sessionId && row == ticket.row && cell == ticket.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, row, cell);
    }
}
