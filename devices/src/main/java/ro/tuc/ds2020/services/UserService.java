package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public boolean saveUser(User userRequest) {
        System.out.println(userRequest.toString());
        try {
            userRepository.save(userRequest);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to save new user. Service-saveUser");
            return false;
        }
    }

    public boolean deleteUser(UUID id) {
        Optional<User> userReceived = userRepository.findById(id);
        if (userReceived.isEmpty()) {
            System.out.println("User with id " + id + " not found!");
            return false;
        }
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println("An error occurs on delete user with id " + id);
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to save new user. Service-saveUser");
            return false;
        }
    }
}
