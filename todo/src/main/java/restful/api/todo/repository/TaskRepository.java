package restful.api.todo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import restful.api.todo.model.Task;

public interface TaskRepository extends CrudRepository<Task,Long> {

    // query used because we don't need to give an entire User
    @Query("SELECT t FROM Task t WHERE t.assignee.matricule = :matricule")
    Iterable<Task> findByAssignee(@Param("matricule") String matricule);

    //query needed cause default findByCompany will check Task.company which does not exist
    @Query("SELECT t FROM Task t WHERE t.assignee.company.id = :companyId")
    Iterable<Task> findByCompany(@Param("companyId") Long companyId);      
}
