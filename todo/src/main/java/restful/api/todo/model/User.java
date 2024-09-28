package restful.api.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_profile") // "user" conflic cause user is a reserved word
public class User {

    @Getter @Id
    @NotBlank private String matricule;
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @NotBlank private String email;

    @ManyToOne
    @JoinColumn(name = "company_id") // Foreign key: multiple users can be assigned to one company
    @NotNull  private Company company; //validate as Non null by JPA


    @Enumerated(EnumType.STRING) // store as a string
    private UserCategory category = UserCategory.STANDARD;

    public User(String matricule, String firstName, String lastName, String email, Company company, UserCategory category) {
        this.matricule = matricule;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
        this.category = category != null ? category : UserCategory.STANDARD;
    }
    
    
   

}

