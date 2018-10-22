/**
 * @description
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date
 **/
package com.tasker.dto.request;

public class SendTaskDTO {

    private Long id;

    private String email;

    public SendTaskDTO() {
    }

    public SendTaskDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "SendTaskDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
