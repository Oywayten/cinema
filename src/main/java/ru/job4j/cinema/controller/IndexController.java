package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.model.User;

/**
 * Контроллер главной страницы.
 * Created by Oywayten on 21.12.2022.
 */
@Controller
public class IndexController {

    /**
     * Метод вызывается на конечной точке /index и делает проверку пользователя.
     * Если не выполнен вход в систему, то пользователю назначается имя Гость.
     *
     * @param model   модель для добавления в нее пользователя из сессии.
     * @param session сессия пользователя.
     * @return строку с наименованием представления для выдачи пользователю.
     */
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
        return "index";
    }

    /**
     * Метод делает недействительной сессию работы пользователя.
     *
     * @param session сессия пользователя.
     * @return строку с редиректом на представление для входа в систему.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}