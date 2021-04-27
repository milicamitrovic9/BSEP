package com.ftn.bsep.model;

public class UserDTO {

    private String name;
    private String lastName;
    private String email;
    private String password;

    public UserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String ime) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

}
