/**
 * @description Клас-сутність. Представляє користувача
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 17.10.2018 22:42
 **/
package com.tasker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tasker.entity.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "person")
public class Person implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String email;

    private Role role;


    @JsonBackReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "PERSON_TASKS",
            joinColumns = @JoinColumn(name = "ID_PERSON"),
            inverseJoinColumns = @JoinColumn(name = "ID_TASK"))
    private List<Task> tasks = new ArrayList<>();

    @NotNull
    @JsonIgnore
    private String password;



    public Person() {
    }

    public Person(@NotNull String email, Role role, @NotNull String password) {
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public Person(@NotNull String firstName, @NotNull String email, @NotNull String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

    public Person(@NotNull String firstName, @NotNull String email, Role role, List<Task> tasks, @NotNull String password) {
        this.firstName = firstName;
        this.email = email;
        this.role = role;
        this.tasks = tasks;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority(role.name())));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public static Person getPerson(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Person) auth.getPrincipal();
    }
}
