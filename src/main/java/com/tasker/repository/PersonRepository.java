/**
 * @description JPA Repository - зв'язується з базою даних,
 * а саме з таблицею person та маніпулює нею
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date 18.10.2018 00:11
 **/
package com.tasker.repository;

import com.tasker.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
