package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Модель данных зала
 * Created by Oywayten on 20.12.2022.
 */
public class Hall {
    /**
     * Идентификатор зала
     */
    private int id;
    /**
     * Название зала
     */
    private String name;
    /**
     * Число рядов в зале
     */
    private int rows;
    /**
     * Число мест в ряду
     */
    private int cells;

    public Hall(int id, String name, int rows, int cells) {
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.cells = cells;
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

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCells() {
        return cells;
    }

    public void setCells(int cells) {
        this.cells = cells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hall hall = (Hall) o;
        return name.equals(hall.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
