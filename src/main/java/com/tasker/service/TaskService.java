/**
 * @description
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date
 **/
package com.tasker.service;

import com.tasker.entity.Task;

import java.util.List;

public interface TaskService {


    void save(Task task);
    void edit(Task task);
    void delete(Long id);
    void send(Long id, String email);
    List<Task>getAllByPerson();
    Task getOneById(Long id);
}
