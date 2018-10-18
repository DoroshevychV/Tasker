/**
 * @description Реалізація інтерфейсу PersonService
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 18.10.2018 00:19
 **/
package com.tasker.service.impl;

import com.tasker.dto.request.LoginPersonDTO;
import com.tasker.dto.request.PersonDTO;
import com.tasker.dto.response.TokenModel;
import com.tasker.entity.Person;
import com.tasker.entity.enums.Role;
import com.tasker.repository.PersonRepository;
import com.tasker.security.TokenUtils;
import com.tasker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PersonSI implements PersonService, UserDetailsService {

    @Value("${lgs.token.header}")
    private String tokenHeader;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Qualifier("personSI")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public TokenModel save(PersonDTO personDTO) throws IllegalArgumentException {
        System.out.println(tokenHeader);

        /** Name's length must be 2-15 symbols and  include only letters */
        if (personDTO.getFirstName().length() >= 2
                && personDTO.getFirstName().length() <= 15
                && personDTO.getFirstName().chars().allMatch(Character::isLetter)) {
            /**Email's length must be 5-35 symbols and include "@" and "." */
            if (personDTO.getEmail().length() >= 5
                    && personDTO.getEmail().length() <= 35
                    && personDTO.getEmail().contains("@")
                    && personDTO.getEmail().contains(".")) {

                /**Password's length must be 8-28 symbols*/
                if (personDTO.getPassword().length() >= 8 && personDTO.getPassword().length() <= 28) {

                    /**Is this email registered?*/
                    if (findByEmail(personDTO.getEmail()) == null) {

                        Person person = new Person(personDTO.getFirstName(), personDTO.getEmail(), personDTO.getPassword());

                        String generatedSecuredPasswordHash = BCrypt.hashpw(person.getPassword(), BCrypt.gensalt(12));

                        person.setPassword("{bcrypt}" + generatedSecuredPasswordHash);
                        person.setRole(Role.ROLE_USER);


                        personRepository.save(person);
                        return authentication(personDTO.getEmail(), personDTO.getPassword());

                    } else {
                        throw new IllegalArgumentException("*A user with such email already registered");
                    }

                } else {
                    throw new IllegalArgumentException("*Password's length must be 8-28 symbols");
                }

            } else {
                throw new IllegalArgumentException("*Email's length must be 5-35 symbols and include \"@\" and \".\" ");
            }

        } else {
            throw new IllegalArgumentException("*Name's length must be 2-15 symbols and not include numbers");
        }


    }

    @Override
    public TokenModel login(LoginPersonDTO loginPersonDTO) {

        Person user = findByEmail(loginPersonDTO.getEmail());

        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);

        if (user != null && passwordEncoder.matches(loginPersonDTO.getPassword(), user.getPassword())) {

            return authentication(loginPersonDTO.getEmail(), loginPersonDTO.getPassword());
        } else {
            throw new IllegalArgumentException("*Not Authorized! Please check the data entered, and try again!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return personRepository.findByEmail(s);
    }

    @Override
    public Person getPersonByID(Long id) {
        return personRepository.getOne(id);
    }

    @Override
    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Override
    public TokenModel authentication(String email, String password) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails =
                this.userDetailsService.loadUserByUsername(email);

        String token = this.tokenUtils.generateToken(userDetails);
        TokenModel tokenModel = new TokenModel("A-Token", token);
        return tokenModel;
    }
}
