/**
 * @description Шаблон класа Persons
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 18.10.2018 01:46
 **/
package com.tasker.dto.request;

public class PersonDTO {
    private String firstName;
    private String email;
    private String password;

    public PersonDTO() {
    }

    public PersonDTO(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
        return "PersonDTO{" +
                "firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
