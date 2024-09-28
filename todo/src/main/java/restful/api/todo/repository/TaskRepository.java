package restful.api.todo.repository;

import org.springframework.data.repository.CrudRepository;
import restful.api.todo.model.Task;

public interface TaskRepository extends CrudRepository<Task,Long> {

    public Iterable<Task> findByAssignee(String userMatricule);
    public Iterable<Task> findByCompany(Long compcanyId);
    
}
