/**
 * @description Controller for person
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 18.10.2018 14:19
 **/
package com.tasker.controller;

import com.tasker.dto.request.LoginPersonDTO;
import com.tasker.dto.request.PersonDTO;
import com.tasker.dto.response.PersonDetailsDTO;
import com.tasker.dto.response.TokenModel;
import com.tasker.entity.Person;
import com.tasker.service.PersonService;
import com.tasker.service.impl.PersonSI;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

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
    @PostMapping("/registration")
    public TokenModel registration(@RequestBody PersonDTO personDTO) {
        return personSI.save(personDTO);
    }

    @PostMapping("/login")
    public TokenModel login(@RequestBody LoginPersonDTO loginPersonDTO) {
        return personSI.login(loginPersonDTO);
    }

    @GetMapping("/user/full")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Person getUser() {
        return personSI.getUser();
    }

    @GetMapping("user/name")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getUsersName() {
        String name = personSI.getUser().getFirstName();
        return JSONObject.quote(name);
    }

    @GetMapping("user/email")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getUsersEmail() {
        return personSI.getUser().getEmail();
    }
//
//    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//            SecurityContextHolder.getContext().setAuthentication(null);
//            System.out.println((Person) auth.getPrincipal());
//        }
//        return "redirect:/";
//
//
//
//    }


    @GetMapping("/user/details")
    @PreAuthorize("hasRole('ROLE_USER')")
    public PersonDetailsDTO personDetails() {
        return personSI.personDetails();
    }


}
