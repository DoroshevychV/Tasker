/**
 * @description Controller for person
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 18.10.2018 14:19
 **/
package com.tasker.controller.person;

import com.tasker.dto.request.LoginPersonDTO;
import com.tasker.dto.request.PersonDTO;
import com.tasker.dto.response.TokenModel;
import com.tasker.entity.Person;
import com.tasker.service.PersonService;
import com.tasker.service.impl.PersonSI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    PersonSI personSI;

    /**
     * Метод для реєстрації користувача
     *
     * @param personDTO Вхідні дані зі сторони клієнта
     * @return Вдало зареєстровано чи ні
     */
    @PostMapping("/user/registration")
    public TokenModel registration(@RequestBody PersonDTO personDTO) {
        return personSI.save(personDTO);
    }

    @PostMapping("/user/login")
    public TokenModel login(@RequestBody LoginPersonDTO loginPersonDTO) {
        return personSI.login(loginPersonDTO);
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Person getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person person = (Person) auth.getPrincipal();
        return person;
    }


}
