/**
 * @description
 * @author Vadym Doroshevych
 * @email doroshevychv@gmail.com
 * @date
 **/
package com.tasker.editor;

import com.tasker.entity.Task;
import com.tasker.service.TaskService;

import java.beans.PropertyEditorSupport;

public class TaskEditor extends PropertyEditorSupport {

    private final TaskService taskService;

    public TaskEditor(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void setAsText(String s) throws IllegalArgumentException {
        Task task = taskService.getOneById(Long.parseLong(s));
        setValue(task);
    }
}
