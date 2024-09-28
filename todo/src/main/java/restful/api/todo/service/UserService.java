package restful.api.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restful.api.todo.dto.UserDto;
import restful.api.todo.exception.PermissionDeniedException;
import restful.api.todo.mapper.UserMapper;
import restful.api.todo.model.User;
import restful.api.todo.repository.UserRepository;

/**
 * UserService
 */
@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    public UserDto addUser(UserDto userDto) {
        User user = userMapper.convertToEntity(userDto);
        var addedUser = repository.save(user);
        return userMapper.convertToDto(addedUser);
    }

    public UserDto getUser(String  userMatricule) {
         User user = repository.findById(userMatricule)
                .orElseThrow(()
                 -> new PermissionDeniedException("User not found with ID: " + userMatricule));
        return userMapper.convertToDto(user);
    }
}