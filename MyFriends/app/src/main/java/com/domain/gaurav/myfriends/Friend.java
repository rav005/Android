package com.domain.gaurav.myfriends;

/**
 * Created by gaurav on 22/07/15.
 */
public class Friend {
    private int id;
    private String name;
    private String email;
    private String phone;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int newId) {
        id = newId;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public void setPhone(String newPhone) {
        phone = newPhone;
    }

}
