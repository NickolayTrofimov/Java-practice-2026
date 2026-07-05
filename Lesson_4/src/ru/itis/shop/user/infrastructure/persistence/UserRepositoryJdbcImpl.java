package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.config.DatabaseConfig;
import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.sql.*;
import java.util.*;

public class UserRepositoryJdbcImpl implements UserRepository {

    public UserRepositoryJdbcImpl() {
    }

    @Override
    public void save(User user) {
        System.out.println("Заглушка");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        System.out.println("Заглушка");
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id) {
        System.out.println("Заглушка");
        return Optional.empty();
    }

    @Override
    public boolean updateProfileDescription(String email, String newDescription) {
        System.out.println("Заглушка");
        return false;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from account order by name";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("profile_description")
                );
                users.add(user);
            }

            System.out.println("Найдено пользователей: " + users.size());

        } catch (SQLException e) {
            System.err.println("Ошибка при получении всех пользователей: " + e.getMessage());
            e.printStackTrace();
        }

        return users;
    }
}