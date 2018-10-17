/**
 * @description Реалізація інтерфейсу PersonService
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 18.10.2018 00:19
 **/
package com.tasker.service.impl;

import com.tasker.dto.PersonDTO;
import com.tasker.entity.Person;
import com.tasker.repository.PersonRepository;
import com.tasker.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


@Service
@ComponentScan("com.tasker.repository")
public class PersonSI implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void save(Person person) {
        try{
            personRepository.save(person);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete() {
        return false;
    }
}
