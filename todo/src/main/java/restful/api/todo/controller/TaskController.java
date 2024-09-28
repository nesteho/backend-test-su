package restful.api.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import restful.api.todo.dto.TaskDto;
import restful.api.todo.dto.UserDto;
import restful.api.todo.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody UserDto userDto, @RequestBody TaskDto taskDto ) {
        taskDto = taskService.addTask(userDto,taskDto);
        return new ResponseEntity<TaskDto>(taskDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks(@RequestBody UserDto userDto) {
        var tasksDto = taskService.viewTasks(userDto);
        return ResponseEntity.ok(tasksDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@RequestBody UserDto userDto, @PathVariable Long id) {
        var taskDto = taskService.viewTask(userDto, id);
        return ResponseEntity.ok(taskDto);    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@RequestBody UserDto userDto, @PathVariable Long id) {
        var taskDto = taskService.viewTask(userDto, id); 
        taskDto = taskService.updateTask(userDto, taskDto); 
        return ResponseEntity.ok(taskDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@RequestBody UserDto userDto, @PathVariable Long id) {
        var taskDto = taskService.viewTask(userDto, id); 
        taskService.deleteTask(userDto, taskDto); 
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
