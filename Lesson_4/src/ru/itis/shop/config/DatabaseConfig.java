package ru.itis.shop.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String HOST = "localhost";
    private static final String PORT = "2903";
    private static final String DATABASE_NAME = "shop_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "443005Tnd.";

    private static final String URL = String.format(
            "jdbc:postgresql://%s:%s/%s",
            HOST, PORT, DATABASE_NAME
    );

    public static String getUrl() {
        return URL;
    }

    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Драйвер не найден!", e);
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к БД: " + e.getMessage());
            return false;
        }
    }
}