package ru.job4j.cinema.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Класс для работы с данными базы users.
 * Created by Oywayten on 08.11.2022.
 */
@Repository
public class JdbcUserRepository implements UserRepository {
    /**
     * Строка запроса к БД на добавление пользователя
     */
    private static final String ADD =
            "INSERT INTO users (username, password, email, phone) VALUES (?, ?, ?, ?) limit 1";
    /**
     * Строка запроса к БД на получение пользователя по емейлу или паролю
     */
    private static final String FIND_USER_BY_EMAIL_OR_PHONE =
            "select * from users where email = ? or phone = ?";
    /**
     * Логгер для логирования
     */
    private static final Logger LOG = LogManager.getLogger(JdbcUserRepository.class);
    /**
     * Пул подключений к БД
     */
    private final DataSource pool;

    /**
     * Констуктор принимает пул подключений к базе данных и инициализирует им значение переменной pool.
     *
     * @param pool {@link DataSource} пул подключений к базе данных.
     */
    public JdbcUserRepository(DataSource pool) {
        this.pool = pool;
    }

    /**
     * Возвращает пользователя из БД или бросает {@link SQLException}.
     *
     * @param it {@link ResultSet} результаты запроса к БД.
     * @return пользователь типа {@link User}.
     * @throws SQLException при неуспешном получении пользователя.
     */
    @Override
    public User createUser(ResultSet it) throws SQLException {
        return new User(
                it.getInt("id"),
                it.getString("username"),
                it.getString("password"),
                it.getString("email"),
                it.getString("phone"));
    }

    /**
     * Добавляет пользователя. При успешном сохранении возвращает {@link Optional} с
     * объектом пользователя {@link User}, у которого проинициализировано id. Иначе возвращает {@link Optional#empty()}.
     * Сохранение может не произойти, если сохраняется пользователь
     * у которого логин или почта, или телефон совпадает с другим пользователей.
     *
     * @param user пользователь для добавления.
     * @return {@link Optional#of(Object)} при успешном сохранении, иначе {@link Optional#empty()}.
     */
    @Override
    public Optional<User> add(User user) {
        Optional<User> optionalUser = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                    optionalUser = Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return optionalUser;
    }

    /**
     * Находит пользователя по телефону или почте. Смотря, что найдет первым..
     *
     * @param email почта искомого пользователя.
     * @param phone телефон искомого пользователя.
     * @return Optional.of(user) при успешном поиске, иначе {@link Optional#empty()}.
     */
    @Override
    public Optional<User> findUserByEmailOrPhone(String email, String phone) {
        Optional<User> optionalUser = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_USER_BY_EMAIL_OR_PHONE)) {
            ps.setString(1, email);
            ps.setString(2, phone);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    optionalUser = Optional.of(createUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return optionalUser;
    }
}
