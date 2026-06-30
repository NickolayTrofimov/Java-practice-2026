package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    public UserFileRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(user.getId() + "|" +
                    user.getEmail() + "|" +
                    user.getPassword() + "|" +
                    user.getProfileDescription());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<String> findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String userId = parts[0];
                    String email = parts[1];
                    if (userId.equals(id)) {
                        return Optional.of(email);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
        System.out.println("Пользователь с id " + id + " не найден");
        return Optional.empty();
    }
}
