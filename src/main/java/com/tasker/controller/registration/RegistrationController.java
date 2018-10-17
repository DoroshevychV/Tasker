/**
 * @description Цей клас-контроллер, який відповідає за реєстрацію користувача в системі
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 17.10.2018 22:40
 **/
package com.tasker.controller.registration;

import com.tasker.dto.PersonDTO;
import com.tasker.entity.Person;
import com.tasker.service.impl.PersonSI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/registration")
public class RegistrationController {

@Autowired
private PersonSI personSI;

    @PostMapping
    public String registration(@RequestBody PersonDTO p){
        System.out.println(p.getFirstName());
        System.out.println(p.getEmail());
        System.out.println(p.getPassword());
        Person person = new Person(p.getFirstName(),p.getEmail(),p.getPassword());
        personSI.save(person);
        return "OK";
    }
}
