package restful.api.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import restful.api.todo.repository.TaskRepository;
import restful.api.todo.dto.TaskDto;
import restful.api.todo.model.Task;

public class TaskService {
    
    private final TaskRepository repository;
    private PermissionService permissionService;

    @Autowired
    public TaskService(TaskRepository repository, PermissionService permissionService) {
        this.repository = repository;
        this.permissionService = permissionService;
        
    }

    public List<TaskDto> getTasks() {
        var tasks = new ArrayList<TaskDto>();
        repository.findAll().forEach(task -> {
            var t = new TaskDto(
                Long.getLong(task.getId()), task.getTitle(), task.getDescription(), task.getDueDate());
            tasks.add(t);
        } );
        return tasks;
    }

    public Task addTask() {
        return null;
    }

    public Task updateTask() {
        return null;
    }

    public void deleteTask() {

    }

}
