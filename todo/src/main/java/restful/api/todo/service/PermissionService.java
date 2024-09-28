package restful.api.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restful.api.todo.exception.PermissionDeniedException;
import restful.api.todo.model.Task;
import restful.api.todo.model.TaskStatus;
import restful.api.todo.model.User;
import restful.api.todo.model.UserCategory;
import restful.api.todo.repository.TaskRepository;
import restful.api.todo.repository.UserRepository;


/*
    Standard users:  Can only view, create, update, and delete THEIR own tasks.
    Company-Admin users:  Can manage their own tasks and also view/manage tasks of users within their company.
    Super Users:  Can view and manage all tasks across all companies
 */
@Service
public class PermissionService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    
    @Autowired
    public PermissionService(TaskRepository taskRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public void validateCreateTask(User user){
        checkUser(user);  // every registered user can create a task
    }
    public void validateViewTasks(User user){
        checkUser(user);  
    }
    
    
    public Task validateViewTask(User user, Long taskId) { // wants single task
        checkUser(user); 

        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new PermissionDeniedException("Task not found with ID: " + taskId)); // Check if the task with given ID exists

        validateTaskOwnership(user, task, "view");
        return task;
    }

    public void validateUpdateTask(User user, Task task){
        validateTaskOwnership(user, task, "update");

    }
     public void validateDeleteTask(User user, Task task){
        validateTaskOwnership(user, task, "delete");
        
    }

    private void checkUser(User user){
        if (user == null) {
            throw new IllegalArgumentException(" This task : the user is not recognized.");
        }
        if (user.getCompany() == null) {
            throw new PermissionDeniedException( "The user is not associated with a company.");
        }
        if (!userRepository.existsById(user.getMatricule())) {
            throw new PermissionDeniedException(" The user with matricule " + user.getMatricule() +  " does not exist in the system.");
        } 
    } 

    private void checkTask(Task task){
        if (task == null) {
            throw new PermissionDeniedException("The task is not recognized.");
        }
        if (task.getStatus() == TaskStatus.ARCHIVED) {
            throw new PermissionDeniedException("The task is archived.");
        }
        if (!taskRepository.existsById(task.getId())) {
            throw new PermissionDeniedException("The task with ID " + task.getId() + " does not exist in the system.");
        } 
    } 

    private void validateTaskOwnership(User user, Task task, String action){
        
        checkUser(user);
        checkTask(task);
        var userCategory = user.getCategory();
        var taskOwner = task.getAssignee();

        if (userCategory.equals(UserCategory.SUPER_USER) ){
            return;
        }

        if (userCategory.equals(UserCategory.STANDARD)
             && !taskOwner.getMatricule().equals(user.getMatricule()) ) 
        {
            throw new PermissionDeniedException(" Standard users can only " + action 
            + " THEIR own tasks.");
        }

        if (userCategory.equals(UserCategory.COMPANY_ADMIN)
            &&  !taskOwner.getCompany().equals(user.getCompany()) ) 
        {
            throw new PermissionDeniedException("Company-Admin users can only " + action 
                    + " their own tasks and tasks of users within their company.");   
        }

    }
}
