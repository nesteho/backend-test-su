package restful.api.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String dueDate;

    @ManyToOne
    @JoinColumn(name = "user") // Foreign key: multiple tasks can be assigned to one user
    @NotNull
    private User assignee;

    public Task(String title, String description, String dueDate, User assignee) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.assignee = assignee;
    }
 
}
