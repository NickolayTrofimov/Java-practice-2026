//Подсматривал в нейронке, потому что не было возможности ставить ultimate

package ru.itis.shop.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        System.out.println("Инициализация структуры базы данных...");

        try (Connection conn = DatabaseConfig.getConnection()) {
            createTables(conn);
            System.out.println("Инициализация БД завершена успешно!");

        } catch (SQLException e) {
            System.err.println("Ошибка инициализации БД: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {

            // Таблица пользователей
            String createUsersTable = """
                create table account (
                    id varchar(36) primary key,
                    name varchar(100) not null,
                    email varchar(100) unique not null,
                    password varchar(100) not null,
                    profile_description varchar
                )
            """;
            stmt.executeUpdate(createUsersTable);
            System.out.println("Таблица 'account' создана");
        }
    }

    private static void insertInitialData(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {


            String checkData = "select count(*) from account";
            var rs = stmt.executeQuery(checkData);
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                String testData = """
                    insert into account (id, name, email, password, profile_description) values 
                        ('admin-001', 'Администратор', 'admin@shop.ru', 'admin123', 'Администратор'),
                        ('user-001', 'Иван Петров', 'ivan@mail.ru', 'pass123', 'Программист'),
                        ('user-002', 'Мария Смирнова', 'maria@mail.ru', 'pass456', 'Дизайнер'),
                        ('user-003', 'Петр Сидоров', 'petr@mail.ru', 'pass789', 'Тестировщик')
                    on conflict (email) do nothing
                """;
                int rows = stmt.executeUpdate(testData);
                System.out.println("Добавлено тестовых пользователей: " + rows);
            } else {
                System.out.println("Тестовые данные уже существуют (" + count + " пользователей)");
            }
        }
    }
}