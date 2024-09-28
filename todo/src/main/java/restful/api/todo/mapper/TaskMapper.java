package restful.api.todo.mapper;


import org.springframework.stereotype.Component;
import lombok.NoArgsConstructor;
import restful.api.todo.dto.TaskDto;
import restful.api.todo.model.Task;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class TaskMapper {

    public TaskDto convertToDto(Task task) {
        if (task == null) return null;
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        return dto;
        }

    public Task convertToEntity(TaskDto taskDto) {

        if (taskDto == null) return null;
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setStatus(taskDto.getStatus());
        return task;
    }

    public List<TaskDto> convertToDtoList(List<Task> tasks) {
    return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
