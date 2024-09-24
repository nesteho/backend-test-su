package restful.api.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Getter @Id
    @NotBlank private String matricule;
    @Getter
    @NotBlank private String firstName;
    @Getter
    @NotBlank private String lastName;

    @ManyToOne
    @JoinColumn(name = "company_id") // Foreign key: multiple users can be assigned to one company
    @NotNull  private Company company; //validate as Non null by JPA

    public enum UserCategory {
        STANDARD,
        COMPANY_ADMIN,
        SUPER_USER
    }
    @Enumerated(EnumType.STRING) // store as a string
    private UserCategory category = UserCategory.STANDARD;

    public User(String matricule, String firstName, String lastName, Company company, UserCategory category) {
        this.matricule = matricule;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.category = category != null ? category : UserCategory.STANDARD;
    }
    
    
   

}

