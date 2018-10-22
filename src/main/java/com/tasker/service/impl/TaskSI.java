/**
 * @description
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date
 **/
package com.tasker.service.impl;

import com.tasker.entity.Person;
import com.tasker.entity.Task;
import com.tasker.repository.PersonRepository;
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
    @Autowired
    private PersonRepository personRepository;


    @Override
    public void save(Task task) {
        if (task != null) {
            if (task.getTitle().length() >= 2 && task.getTitle().length() <= 30) {
                if (task.getDescription().length() >= 10 && task.getDescription().length() <= 200) {
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
        Task taskFromDatabase = taskRepository.getOne(task.getId());
        Person thisPerson = Person.getPerson();
        for (Person person : taskFromDatabase.getPersons()) {
            if (person.getEmail().equals(thisPerson.getEmail())) {
                taskFromDatabase.setTitle(task.getTitle());
                taskFromDatabase.setDescription(task.getDescription());
                taskRepository.save(taskFromDatabase);
                return;
            }
        }
        throw new IllegalArgumentException("You do not have such rights!");

    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void send(Long id, String email) {

        if (email.length() >= 5
                && email.length() <= 35
                && email.contains("@")
                && email.contains(".")) {

            Person thisPerson = Person.getPerson();

            if (!email.equals(thisPerson.getEmail())) {

                Person person = personRepository.findByEmail(email);

                if (person != null) {

                    Task task = getOneById(id);

                    if (task != null) {

                        if (thisPerson != null) {

                            for (Task verifying : person.getTasks()) {
                                if (verifying.equals(task)) {
                                    throw new IllegalArgumentException("User already has this task!");
                                } else {
                                    continue;
                                }
                            }

                            List<Person> personList = task.getPersons();
                            personList.add(person);
                            task.setPersons(personList);
                            taskRepository.save(task);
                            System.out.println("STATUS OK SERVICE END");

                        } else {
                            throw new IllegalArgumentException("You do not have such rights!");
                        }


                    } else {
                        throw new IllegalArgumentException("Such a task does not exist!");
                    }
                } else {
                    throw new IllegalArgumentException("This e-mail is not registered!");
                }
            } else {
                throw new IllegalArgumentException("You can not send a task to yourself!");
            }
        } else {
            throw new IllegalArgumentException("*Email's length must be 5-35 symbols and include \"@\" and \".\" ");
        }

    }

    @Override
    public List<Task> getAllByPerson() {
        Person person = Person.getPerson();
        return person.getTasks();
    }

    @Override
    public Task getOneById(Long id) {
        return taskRepository.getOne(id);

    }
}
