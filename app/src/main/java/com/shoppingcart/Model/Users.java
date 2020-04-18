package com.shoppingcart.Model;


public class Users {
    private String name, email, address, phone, password, confirm_pass;

    public Users()
    {

    }

    public Users(String name, String email , String address, String phone, String password, String confirm_pass) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.confirm_pass = confirm_pass;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_pass() {
        return confirm_pass;
    }

    public void setImage(String confirm_pass) {
        this.confirm_pass = confirm_pass;
    }


}
