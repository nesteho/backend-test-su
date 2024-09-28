package restful.api.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import restful.api.todo.model.UserCategory;

@Data
@AllArgsConstructor
public class UserDto {

    @Getter
    private String matricule;
    private String firstName;
    private String lastName;
    private String email;
    private UserCategory category;
    private CompanyDto company;

    public UserDto(String firstName, String lastName, String email, CompanyDto companyDto
    , UserCategory category) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = companyDto; 
        this.category = category;
    }

}
