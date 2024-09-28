package restful.api.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restful.api.todo.repository.TaskRepository;
import restful.api.todo.dto.TaskDto;
import restful.api.todo.dto.UserDto;
import restful.api.todo.exception.PermissionDeniedException;
import restful.api.todo.mapper.TaskMapper;
import restful.api.todo.mapper.UserMapper;
import restful.api.todo.model.Task;
import restful.api.todo.model.User;

@Service
public class TaskService {

    private final TaskRepository repository;
    private PermissionService permissionService;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    @Autowired
    public TaskService(TaskRepository repository, PermissionService permissionService, TaskMapper taskMapper, UserMapper userMapper) {
        this.repository = repository;
        this.permissionService = permissionService;
        this.taskMapper = taskMapper;
        this.userMapper = userMapper;

    }

    public List<TaskDto> viewTasks(UserDto userdDto) {
        try {
            User user = userMapper.convertToEntity(userdDto);

            permissionService.validateViewTasks(user);
            Iterable<Task> tasksIterable; // spring returns iterable for collections of task

            switch (user.getCategory()) {
                case STANDARD:
                    tasksIterable = repository.findByAssignee(user.getMatricule());
                    break;
                case COMPANY_ADMIN:
                    tasksIterable = repository.findByCompany(user.getCompany().getId());
                    break;
                case SUPER_USER:
                    tasksIterable = repository.findAll();
                    break;

                default:
                    throw new PermissionDeniedException("Uknown User Category");
            }

            List<Task> taskList = new ArrayList<>();
            tasksIterable.forEach(taskList::add);
            return taskMapper.convertToDtoList(taskList);

        } catch (PermissionDeniedException e) {
            throw new PermissionDeniedException("You do not have permission to view  any task. " + e.getMessage());
        }
    }

    public TaskDto viewTask(UserDto userdDto, Long taskId) {
        try {
            User user = userMapper.convertToEntity(userdDto);
            permissionService.validateViewTask(user, taskId);
            var optionalTask = repository.findById(taskId);

            // Since we've validated, taskOptional should always be present; however, if
            // it's not:
            var task =  optionalTask.orElseThrow(() -> new PermissionDeniedException("Task not found with ID: " + taskId));
            return taskMapper.convertToDto(task);

        } catch (PermissionDeniedException e) {
            throw new PermissionDeniedException("You do not have permission to create this task. " + e.getMessage());
        }
    }

    public TaskDto addTask(UserDto userdDto, TaskDto taskDto) {

        try {
            Task task = taskMapper.convertToEntity(taskDto);
            User user = userMapper.convertToEntity(userdDto);
            permissionService.validateCreateTask(user);
            var addedTask =  repository.save(task);
            return taskMapper.convertToDto(addedTask);

        } catch (PermissionDeniedException e) {
            throw new PermissionDeniedException("You do not have permission to create this task. " + e.getMessage());
        }
    }

    public TaskDto updateTask(UserDto userdDto, TaskDto taskDto) {

        try {
            Task task = taskMapper.convertToEntity(taskDto);
            User user = userMapper.convertToEntity(userdDto);
            permissionService.validateUpdateTask(user, task);

            var updatedTask =  repository.findById(task.getId())
                    .map(existingTask -> {
                        existingTask.setTitle(task.getTitle());
                        existingTask.setDescription(task.getDescription());
                        existingTask.setDueDate(task.getDueDate());
                        existingTask.setStatus(task.getStatus());

                        return repository.save(existingTask);
                    })
                    .orElseThrow(() -> new PermissionDeniedException("Task not found with ID: " + task.getId()));
            return taskMapper.convertToDto(updatedTask);

        } catch (PermissionDeniedException e) {
            throw new PermissionDeniedException("You do not have permission to update this task. " + e.getMessage());
        }
    }

    public void deleteTask(UserDto userdDto, TaskDto taskDto) {

        try {
            Task task = taskMapper.convertToEntity(taskDto);
            User user = userMapper.convertToEntity(userdDto);
            permissionService.validateCreateTask(user);
            repository.delete(task);

        } catch (PermissionDeniedException e) {
            throw new PermissionDeniedException("You do not have permission to delete this task. " + e.getMessage());
        }

    }

}
