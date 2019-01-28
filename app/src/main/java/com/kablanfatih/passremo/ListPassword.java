package com.kablanfatih.passremo;

import java.util.ArrayList;

public class ListPassword {

    private String title;
    private String name;
    private String password;
    private String email;

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

    public static ArrayList<ListPassword> getData() {

        ArrayList<ListPassword> dataList = new ArrayList<>();
        int[] resimler = {
                R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimary
        };

        for (int i = 1; i < resimler.length; i++) {

            ListPassword gecici = new ListPassword();
            gecici.setTitle("Parola " + i);
            gecici.setName("Açıklama " + i);
            dataList.add(gecici);
        }
        return dataList;
    }

}
