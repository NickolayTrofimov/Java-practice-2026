package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

public class UserFileRepository implements UserRepository {

    private final String fileName;

    private final UserMapper userMapper;

    public UserFileRepository(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String id = UUID.randomUUID().toString();
            user.setId(id);
            writer.write(userMapper.toLine(user));
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line = reader.readLine();
            while (line != null) {
                User user = userMapper.fromLine(line);
                if (user.getEmail().equals(email)) {
                    return Optional.of(user);
                }
                line = reader.readLine();
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                User user = userMapper.fromLine(line);
                if (user.getId().equals(id)) {
                    return Optional.of(user);
                }
                line = reader.readLine();
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean updateProfileDescription(String email, String newDescription) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".tmp"))) {
            String line;
            boolean userFound = false;
            while ((line = reader.readLine()) != null) {
                User user = userMapper.fromLine(line);
                if (user.getEmail().equals(email)) {
                    userFound = true;
                    user.setProfileDescription(newDescription);
                    writer.write(userMapper.toLine(user));
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
            if (!userFound) {
                return false;
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName + ".tmp"));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return true;
    }
}
