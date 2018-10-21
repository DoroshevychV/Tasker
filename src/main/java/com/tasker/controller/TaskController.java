/**
 * @description
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date
 **/
package com.tasker.controller;

import com.tasker.editor.TaskEditor;
import com.tasker.entity.Task;
import com.tasker.service.TaskService;
import com.tasker.service.impl.TaskSI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TaskController {
    @Autowired
    TaskSI taskSI;

    @InitBinder
    protected void initBinderTask(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Task.class, new TaskEditor(taskSI));
    }
    @PostMapping("/task/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus (value = HttpStatus.OK)
    public void create(@RequestBody Task task){
        System.out.println(task.getTitle()+" "+task.getDescription());
        taskSI.save(task);
    }

    @PutMapping("/task/edit")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus (value = HttpStatus.OK)
    public void edit(@RequestBody Task task){
        System.out.println("Ediiiiiitittttttt");
        taskSI.edit(task);
        System.out.println("Ediiiiiitittttttt2");
    }


    @GetMapping("/task/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Task> getAllUsersTasks(){
//        System.out.println("Controller Start");
        List<Task> list = taskSI.getAllByPerson();
//        System.out.println("Controller END");
        return list;
    }

    @GetMapping("/task/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Task getTaskDetails(@PathVariable Long id){
        return taskSI.getOneById(id);
    }

    @DeleteMapping("/task/delete")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus (value = HttpStatus.OK)
    public void delete(@RequestBody Long id){
        taskSI.delete(id);
    }

}
