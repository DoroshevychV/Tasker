/**
 * @description Registration Service - інтерфейс,
 * реалізація якого дасть можливість занесення/видалення
 * користувача до/з бази
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 18.10.2018 00:15
 **/
package com.tasker.service;

import com.tasker.dto.request.LoginPersonDTO;
import com.tasker.dto.request.PersonDTO;
import com.tasker.dto.response.PersonDetailsDTO;
import com.tasker.dto.response.TokenModel;
import com.tasker.entity.Person;

public interface PersonService {

    TokenModel save(PersonDTO person);

//    boolean login(LoginPersonDTO loginPersonDTO);

    Person getPersonByID(Long id);

    TokenModel login(LoginPersonDTO loginPersonDTO);

    Person findByEmail(String email);


    TokenModel authentication(String email, String password);

    PersonDetailsDTO personDetails();

    Person getUser();
}
