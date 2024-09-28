package restful.api.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyDto {
    
    private Long id; 
    private String name; 
    private String address; 
}
