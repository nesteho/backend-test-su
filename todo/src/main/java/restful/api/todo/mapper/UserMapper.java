package restful.api.todo.mapper;

import org.springframework.stereotype.Component;

import restful.api.todo.model.Company;
import restful.api.todo.model.User;
import restful.api.todo.dto.CompanyDto;
import restful.api.todo.dto.UserDto;

@Component
public class UserMapper {
    
    public UserDto convertToDto(User user) {

        if (user == null) return null;
        var company = user.getCompany();
        var companydto = new CompanyDto(company.getId(), company.getName(), company.getAddress());
        return new UserDto( user.getMatricule(), user.getFirstName(), 
        user.getLastName(),companydto , user.getCategory());
 
    }

    public User convertToEntity(UserDto dto) {
        
        if (dto == null) return null;
        var companydto = dto.getCompany();
        var company = new Company(companydto.getId(), companydto.getName(), companydto.getAddress());
        return new User(dto.getMatricule(), dto.getFirstName(), dto.getLastName()
        , dto.getEmail(), company, dto.getCategory());
    
    }
}

