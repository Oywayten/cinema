package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.User;
import static org.assertj.core.api.Assertions.*;

/**
 * Created by Oywayten on 10.01.2023.
 */
class UserControllerTest {

    @Test
    public void testTest() {
        User user = new User();
        assertThat(new User()).isEqualTo(user);
    }

}