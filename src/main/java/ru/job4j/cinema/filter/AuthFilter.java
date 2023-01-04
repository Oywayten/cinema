package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Oywayten on 04.01.2022.
 * Класс-фильтр производит проверку доступа.
 */
@Component
public class AuthFilter implements Filter {
    /**
     * Список окончаний uri разрешенных для доступа без аутентификации.
     */
    private static final Set<String> ALLOWED_PAGE_SET = Set.of(
            "loginPage",
            "login",
            "addUser",
            "registration",
            "index");

    /**
     * Метод выполняет фильтрацию доступа по наличию пользователя в сессии и проверке совпадений
     * страниц, к которым разрешен свободный доступ.
     * Если страница совпадает с разрешенными {@link #ALLOWED_PAGE_SET}, то происходит вызов ресурсов и выход из метода
     * с return.
     * Если же страница не совпадает с {@link #ALLOWED_PAGE_SET}, то происходит проверка пользователя
     * и redirect, если это не зарегистрированный пользователь.
     * @param request запрос {@link ServletRequest}
     * @param response ответ {@link ServletResponse}
     * @param chain цепочка вызовов {@link FilterChain}
     * @throws IOException если во время обработки произошла ошибка, связанная с вводом-выводом.
     * @throws ServletException если произошло исключение, которое мешает нормальной работе filterChain.
     */
    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (pageCheck(uri)) {
            chain.doFilter(req, res);
            return;
        }
        // TODO: 04.01.2023 AuthFilter.doFilter() -  Проверить, можно ли тут не if-if, а через if-else
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }

    /**
     * Вспомогательный метод для проверки совпадения окончания uri страницы со списком разрешенных значений.
     * @param uri переданный адрес страницы для проверки
     * @return true, если совпало с любым первым разрешенным вариантом, иначе - false.
     */
    private boolean pageCheck(String uri) {
        return ALLOWED_PAGE_SET.stream().anyMatch(uri::endsWith);
    }
}
