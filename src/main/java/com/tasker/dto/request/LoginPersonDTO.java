/**
 * @description Шаблон класу для запита логінування користувача
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 18.10.2018 17:32
 **/
package com.tasker.dto.request;

public class LoginPersonDTO {

    private String email;

    private String password;

    public LoginPersonDTO() {
    }

    public LoginPersonDTO(String email, String password) {
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "LoginPersonDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
