/**
 * @description Class-Entity
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 21.10.2018 20:15
 **/
package com.tasker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "task")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PERSON_TASKS",
            joinColumns = @JoinColumn(name = "ID_TASK"),
            inverseJoinColumns = @JoinColumn(name = "ID_PERSON"))
    private List<Person> persons = new ArrayList<>();

    public Task() {
    }

    public Task(String title, String description, List<Person> persons) {
        this.title = title;
        this.description = description;
        this.persons = persons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", persons=" + persons +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Task guest = (Task) o;
        return id == guest.id
                && (title == guest.title
                || (title != null
                &&title.equals(guest.getTitle())))
                && (description == guest.description
                || (description != null
                && description .equals(guest.getDescription())
        ));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getPersons());
    }
}
