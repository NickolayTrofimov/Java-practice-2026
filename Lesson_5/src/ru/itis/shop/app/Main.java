package ru.itis.shop.app;

import ru.itis.shop.infrastructure.persistence.jdbc.DriverManagerDataSource;
import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.jdbc.UserRepositoryJdbcImpl;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource  = new DriverManagerDataSource("jdbc:postgresql://localhost:2903/shop_db",
                "postgres", "443005Tnd.");

        UserRepository userRepository = new UserRepositoryJdbcImpl(dataSource);
        UserService userService = new UserService(userRepository);
        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
