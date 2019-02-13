package com.kablanfatih.passremo;

public class ListPassword {

    private String title;
    private String name;
    private String password;
    private String email;

    public ListPassword() {
    }

    public ListPassword(String title, String name, String password, String email) {
        this.title = title;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
