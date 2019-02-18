package com.kablanfatih.passremo;

public class ListPassword {

    private String title;
    private String name;
    private String password;
    private String email;
    private String recordId;

    public ListPassword() {
    }

    public ListPassword(String title, String name, String password, String email, String recordId) {
        this.title = title;
        this.name = name;
        this.password = password;
        this.email = email;
        this.recordId = recordId;
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
