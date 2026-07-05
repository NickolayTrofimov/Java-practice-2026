package ru.itis.shop.app;

import ru.itis.shop.config.DatabaseConfig;
import ru.itis.shop.config.DatabaseInitializer;
import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.UserRepositoryJdbcImpl;
import ru.itis.shop.user.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        UserRepository userRepository = new UserRepositoryJdbcImpl();
        UserService userService = new UserService(userRepository);
        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}