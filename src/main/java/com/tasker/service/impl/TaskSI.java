/**
 * @description
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date
 **/
package com.tasker.service.impl;

import com.tasker.entity.Person;
import com.tasker.entity.Task;
import com.tasker.repository.TaskRepository;
import com.tasker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskSI implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void save(Task task) {
        if (task != null) {
            if (task.getTitle().length() >= 2 && task.getTitle().length() <= 30) {
                if (task.getDescription().length() >= 10 && task.getDescription().length() <= 200) {
                    System.out.println("CREEEEATE");
                    List<Person> personList = new ArrayList<>();
                    personList.add(Person.getPerson());
                    task.setPersons(personList);
                    taskRepository.save(task);

                } else {
                    throw new IllegalArgumentException("Description must include 10-300 symbols!");
                }

            } else {
                throw new IllegalArgumentException("Title must include 2-30 symbols!");
            }
        } else {
            throw new IllegalArgumentException("Task must not be null!");
        }
    }

    @Override
    public void edit(Task task) {
        System.out.println("Service");
        Task taskFromDatabase = taskRepository.getOne(task.getId());
        Person thisPerson = Person.getPerson();
        for(Person person:taskFromDatabase.getPersons()){
            if(person.getEmail().equals(thisPerson.getEmail())){
                taskFromDatabase.setTitle(task.getTitle());
                taskFromDatabase.setDescription(task.getDescription());
                taskRepository.save(taskFromDatabase);
                System.out.println("Exit service");
                return;
            }
        }
        System.out.println("Continue");
        throw new IllegalArgumentException("You do not have such rights!");

    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getAllByPerson() {
//        System.out.println("Service start");
        Person person = Person.getPerson();
//        System.out.println("Service end");
        return person.getTasks();
    }

    @Override
    public Task getOneById(Long id) {
        return taskRepository.getOne(id);

    }
}
