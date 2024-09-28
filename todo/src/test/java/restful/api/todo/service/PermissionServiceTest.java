package restful.api.todo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import restful.api.todo.exception.PermissionDeniedException;
import restful.api.todo.model.Company;
import restful.api.todo.model.Task;
import restful.api.todo.model.User;
import restful.api.todo.model.UserCategory;
import restful.api.todo.repository.TaskRepository;
import restful.api.todo.repository.UserRepository;

public class PermissionServiceTest {
    private static final String STANDARD_USER_MATRICULE = "001";
    private static final String COMPANY_ADMIN_MATRICULE = "002";
    private static final String SUPER_USER_MATRICULE = "003";

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PermissionService permissionService;

    private User standardUser;
    private User companyAdmin;
    private User superUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        standardUser = createMockUser(STANDARD_USER_MATRICULE, UserCategory.STANDARD);
        companyAdmin = createMockUser(COMPANY_ADMIN_MATRICULE, UserCategory.COMPANY_ADMIN);
        superUser = createMockUser(SUPER_USER_MATRICULE, UserCategory.SUPER_USER);
        
        when(userRepository.existsById(STANDARD_USER_MATRICULE)).thenReturn(true);
        when(userRepository.existsById(COMPANY_ADMIN_MATRICULE)).thenReturn(true);
        when(userRepository.existsById(SUPER_USER_MATRICULE)).thenReturn(true);
    }

    // Helper method to create a mocked User
    private User createMockUser(String matricule, UserCategory category) {
        User user = mock(User.class);
        when(user.getMatricule()).thenReturn(matricule);
        when(user.getCategory()).thenReturn(category);
        when(user.getCompany()).thenReturn(mock(Company.class)); // Mock Company for the user
        return user;
    }

    @Test
    public void testStandardUserCanManageOwnTask() {
        Task task = mock(Task.class);
        when(task.getId()).thenReturn(1L);
        when(task.getAssignee()).thenReturn(standardUser); // Task assigned to the standard user

        // Simulate the task repository returning the task
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        // Test that the permission service allows the standard user to manage their task
        assertDoesNotThrow(() -> permissionService.validateUpdateTask(standardUser, task));
    }

    @Test
    public void testCompanyAdminCanManageOwnTask() {
        Task task = mock(Task.class);
        when(task.getAssignee()).thenReturn(companyAdmin); // Task assigned to the company admin

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        assertDoesNotThrow(() -> permissionService.validateUpdateTask(companyAdmin, task));
    }

    @Test
    public void testSuperUserCanManageAnyTask() {
        Task task = mock(Task.class);
        when(task.getAssignee()).thenReturn(mock(User.class)); // Any task assigned to a mocked user

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        assertDoesNotThrow(() -> permissionService.validateUpdateTask(superUser, task));
    }

    @Test
    public void testStandardUserCannotManageOthersTask() {
        Task task = mock(Task.class);
        User otherUser = mock(User.class);
        when(otherUser.getMatricule()).thenReturn("002"); // Task assigned to another user
        when(task.getAssignee()).thenReturn(otherUser);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        assertThrows(PermissionDeniedException.class, () -> permissionService.validateUpdateTask(standardUser, task));
    }

    @Test
    public void testCompanyAdminCannotManageTasksWithoutCompany() {
        Task task = mock(Task.class);
        User otherUser = mock(User.class);
        when(otherUser.getMatricule()).thenReturn("004"); // Task assigned to a different user
        when(task.getAssignee()).thenReturn(otherUser);

        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        assertThrows(PermissionDeniedException.class, () -> permissionService.validateUpdateTask(companyAdmin, task));
    }
}
