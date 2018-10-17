/**
 * @description Registration Service - інтерфейс,
 * реалізація якого дасть можливість занесення/видалення
 * користувача до/з бази
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 18.10.2018 00:15
 **/
package com.tasker.service;

import com.tasker.dto.PersonDTO;
import com.tasker.entity.Person;

public interface PersonService {

    void save(Person person);

    boolean delete();
}
