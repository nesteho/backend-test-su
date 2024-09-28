package restful.api.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class TaskDto {
    @Getter
    private Long id;
    private String title;
    private String description;
    private String dueDate;
}
