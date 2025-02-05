package developer.backend.CRUD.service;

import developer.backend.CRUD.controller.DTO.CreateUserDto;
import developer.backend.CRUD.controller.DTO.UpdateUserDto;
import developer.backend.CRUD.entity.User;
import developer.backend.CRUD.repository.UserRepository;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID CreateUser(CreateUserDto createUserDto) {

        var entity = new User(
                UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null
        );

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto){

        var userEntity = userRepository.findById(UUID.fromString(userId));

        if(userEntity.isPresent()) {
            var user = userEntity.get();
            if(updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if(updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }
            userRepository.save(user);
        }
    }

    public void deleteUserById(String userId) {
        var userExists = userRepository.existsById(UUID.fromString(userId));

        if(userExists) {
            userRepository.deleteById(UUID.fromString(userId));
        }
    }
}
