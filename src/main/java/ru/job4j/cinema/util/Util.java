package ru.job4j.cinema.util;


import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;

/**
 * Created by Oywayten on 26.12.2022.
 */
public class Util {
    private Util() {
    }

    public static void setUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }
}
