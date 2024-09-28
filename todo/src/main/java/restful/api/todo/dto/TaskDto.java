package restful.api.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import restful.api.todo.model.TaskStatus;

import java.time.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @Getter
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private TaskStatus status;
}
