package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import java.util.Optional;

/**
 * Контроллер пользователей.
 * Created by Oywayten on 21.12.2022.
 */
@Controller
public class UserController {
    /**
     * Сервис пользователей.
     */
    private final UserService userService;

    /**
     * Конструктор принимает сервис пользователей и инициализирует им переменную {@link #userService}.
     *
     * @param userService сервис пользователей {@link UserService} для работы контроллера.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод добавления пользователя на регистрацию.
     * Принимает в {@link Model} аттрибут {@link User} для передачи далее и добавления в него
     * данных на странице представления с формой регистрации пользователя.
     *
     * @param model {@link Model} для добавления в неё атрибута "user".
     * @return {@link String} наименование представления регистрации нового пользователя "addUser"
     */
    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User(0, "Заполните поле", "Заполните поле", "Заполните поле", "Заполните поле"));
        return "addUser_view";
    }

    /**
     * Метод регистрации пользователя.
     * Принимает {@link Model} в качестве параметра и создает на её основе пользователя {@link User}.
     * Затем добавляет его в хранилище. При успешном добавлении, перенаправляет пользователя на представление сеансов.
     * А при неуспешном - на представление с формой регистрации пользователя. Также при неуспешном добавлении добавляет
     * в модель сообщение, что пользователь с таким телефоном или почтой уже существет.
     *
     * @param user               Параметр типа {@link User} для получения пользователя из модели {@link Model}.
     * @param redirectAttributes {@link RedirectAttributes} для передачи атрибута "message" при redirect.
     * @return {@link String} с перенаправлением на представление сеансов при успешном добавлении
     * в хранилище {@link User}, в ином случае - на представление регистрации нового пользователя.
     */
    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, final RedirectAttributes redirectAttributes) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Пользователь с таким телефоном или почтой уже существует");
            return "redirect:/addUser";
        }
        return "redirect:/posts";
    }

    /**
     * Принимает атрибут {@link Model} для добавления в него атрибута "fail" и атрибут {@link Boolean} fail, который
     * парсится из строки get-запроса вида {@code loginPage?fail=email} с помощью аннотации {@link RequestParam}.
     *
     * @param model {@link Model} для добавления в него атрибута "fail" и передачи далее.
     * @param fail  {@link String} парсится из строки запроса и указывает,
     *              что при входе неверно введен телефон {@code fail=phone} или почта {@code fail=email}.
     * @return {@link String} наименование представления логина пользователя в систему.
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) String fail) {
        model.addAttribute("fail", fail != null); //// TODO: 27.12.2022 Обработать как loginPage в dream_job
        return "login_view";
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

    /**
     * Метод входа по почте и паролю.
     *
     * @param user {@link User} для получения пользователя из данных переданной модели с помощью
     *             аннотации {@link ModelAttribute}
     * @param req  {@link HttpServletRequest} HTTP запрос для получения сессии {@link HttpSession}.
     * @return {@link String} с перенаправлением на представление главной страницы.
     */
    @PostMapping("/emailLogin")
    public String loginEmail(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPassword(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=email";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }


    @PostMapping("/phoneLogin")
    public String loginPhone(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPassword(
                user.getPhone(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=phone";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }
}
