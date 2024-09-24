package restful.api.todo.repository;

import org.springframework.data.repository.CrudRepository;
import restful.api.todo.model.User;

public interface UserRepository extends CrudRepository<User,String>{
    
}
