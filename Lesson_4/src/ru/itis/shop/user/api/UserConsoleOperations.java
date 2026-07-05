package ru.itis.shop.user.api;

import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.domain.User;

import java.util.List;
import java.util.Scanner;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "3": {
                findUserById();
            }
            break;
            case "4": {
                updateUserProfile();
            }
            break;
            case "5": {
                showAllUsers();
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. Обновить описание профиля");
        System.out.println("5. Показать всех пользователей");
        System.out.println("0. Выход");
    }

    private void signUp() {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите name:");
        String name = scanner.nextLine();
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(name, email, password, profileDescription);
    }


    private void signIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение");
        } else {
            System.out.println("Email или пароль не верны");
        }
    }

    private void findUserById() {
        System.out.println("Введите id пользователя:");
        String id = scanner.nextLine();

        userService.findById(id).ifPresentOrElse(
                user -> System.out.println("Email пользователя: " + user.getEmail()),
                () -> System.out.println("Пользователь с id " + id + " не найден")
        );
    }

    private void updateUserProfile() {
        System.out.println("Введите email пользователя, данные которого хотите обновить:");
        String email = scanner.nextLine();
        System.out.println("Введите новое описание профиля:");
        String newDescription = scanner.nextLine();

        if (userService.updateProfileDescription(email, newDescription)) {
            System.out.println("Описание профиля обновлено");
        } else {
            System.out.println("Пользователь с email " + email + " не найден");
        }
    }

    private void showAllUsers() {
        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            System.out.println("Пользователей пока нет");
        } else {
            System.out.println("Список всех пользователей:");
            for (User user : users) {
                System.out.println("ID: " + user.getId());
                System.out.println("Имя: " + user.getName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Описание: " + user.getProfileDescription());
            }
            System.out.println("Всего пользователей: " + users.size());
        }
    }
}
