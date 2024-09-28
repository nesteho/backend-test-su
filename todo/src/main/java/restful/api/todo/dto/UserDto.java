package restful.api.todo.dto;

import lombok.Data;
import lombok.Getter;
import restful.api.todo.model.User.UserCategory;

@Data
public class UserDto {

    @Getter
    private String matricule;
    private String firstName;
    private String lastName;
    private String email; // Optional
    private Long companyId;
    private UserCategory userCategory;
}
