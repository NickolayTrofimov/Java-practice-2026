package ru.itis.shop.user.domain;

public class User {

    private String id;
    private String name;
    private String email;
    private String password;

    private String profileDescription;

    public User(String id, String name, String email, String password, String profileDescription) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileDescription = profileDescription;
    }

    public User(String name, String email, String password, String profileDescription) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileDescription = profileDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }
}
