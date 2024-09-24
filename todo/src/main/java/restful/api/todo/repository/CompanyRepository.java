package restful.api.todo.repository;

import org.springframework.data.repository.CrudRepository;
import restful.api.todo.model.Company;

public interface CompanyRepository extends CrudRepository<Company,Long> {
    
}
