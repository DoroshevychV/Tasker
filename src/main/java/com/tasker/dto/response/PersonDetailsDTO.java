/**
 * @description
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date
 **/
package com.tasker.dto.response;

public class PersonDetailsDTO {

    private String name;

    private String email;

    public PersonDetailsDTO() {
    }

    public PersonDetailsDTO(String name, String email) {
        this.name = name;
        this.email = email;
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

    @Override
    public String toString() {
        return "PersonDetailsDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
