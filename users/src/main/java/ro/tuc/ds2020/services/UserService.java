package ro.tuc.ds2020.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ro.tuc.ds2020.config.WebClientConfig;
import ro.tuc.ds2020.entities.RoleType;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.entities.dtos.ListUserResponse;
import ro.tuc.ds2020.entities.dtos.UserRequest;
import ro.tuc.ds2020.entities.dtos.UserResponse;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WebClient webClient;


    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       WebClientConfig webClientConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.webClient = webClientConfig.webClientBuilder().build();
    }

    public UserResponse saveUser(UserRequest userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(RoleType.valueOf(userRequest.getRoleType()))
                .build();
        try {
            Optional<User> checkUser = userRepository.findByUsername(user.getUsername());
            if (checkUser.isPresent()) {
                return null;
            }
            user = userRepository.save(user);
            final boolean[] sync = {false};
            webClient
                    .post()
                    .uri("/api/v1/users/save")
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .doOnSuccess(userResponse -> {
                        sync[0] = true;
                    })
                    .block();
            if (sync[0]) {
                return UserResponse
                        .builder()
                        .name(user.getName())
                        .username(user.getUsername())
                        .roleType(user.getRole().name())
                        .build();
            }
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to save new user. Service-saveUser in catch");
            return null;
        }
        System.out.println("Failed to save new user. Service-saveUser after try-catch");
        return null;
    }

    public ListUserResponse getAllUsers() {
        List<User> users = userRepository.findAll();
        ArrayList<UserResponse> listUsers = new ArrayList<>();
        for (User user : users) {
            UserResponse userResponse = UserResponse
                    .builder()
                    .id(user.getId())
                    .name(user.getName())
                    .username(user.getUsername())
                    .roleType(user.getRole().name())
                    .build();
            listUsers.add(userResponse);
        }
        return new ListUserResponse(listUsers);
    }

    public boolean deleteUser(UUID id) {
        Optional<User> userReceived = userRepository.findById(id);
        if (userReceived.isEmpty()) {
            System.out.println("User with id " + id + " not found!");
            return false;
        }
        try {
            final boolean[] sync = {false};
            webClient.delete()
                    .uri("/api/v1/users/delete/" + id)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .doOnSuccess(userResponse -> {
                        System.out.println("on Succces : " + userResponse);
                        sync[0] = true;
                    })
                    .block();
            if (sync[0]) {
                userRepository.deleteById(id);
                return true;
            }
            System.out.println("An error with devices microservice, try again!");
            return false;
        } catch (Exception e) {
            System.out.println("An error occurs on delete user with id " + id);
            return false;
        }

    }

    public UserResponse updateUser(UserRequest userRequest) {
        Optional<User> checkUser = userRepository.findById(userRequest.getId());
        if (checkUser.isEmpty()) {
            return null;
        }
        User user = checkUser.get();
        user.setName(userRequest.getName() != null ?
                userRequest.getName() : user.getName());
        user.setUsername(userRequest.getUsername() != null ?
                userRequest.getUsername() : user.getUsername());
        user.setPassword(userRequest.getPassword() != null ?
                passwordEncoder.encode(userRequest.getPassword()) : user.getPassword());
        user.setRole(userRequest.getRoleType() != null ?
                RoleType.valueOf(userRequest.getRoleType()) : user.getRole());
        try {
            userRepository.save(user);
            final boolean[] sync = {false};
            webClient
                    .put()
                    .uri("/api/v1/users/update")
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(boolean.class)
                    .doOnSuccess(userResponse -> {
                        sync[0] = true;
                    })
                    .block();
            if (sync[0]) {
                return UserResponse
                        .builder()
                        .name(user.getName())
                        .username(user.getUsername())
                        .roleType(user.getRole().name())
                        .build();
            }
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to update new user. Service-updateUser in catch");
            return null;
        }
        System.out.println("Failed to save new user. Service-updateUser after try-catch");
        return null;
    }

    public UserResponse getUserById(UUID id) {
        Optional<User> userReceived = userRepository.findById(id);
        if (userReceived.isEmpty()) {
            System.out.println("User with id " + id + " not found!");
            return null;
        }
        try {
            User user = userReceived.get();

            return UserResponse
                    .builder()
                    .id(user.getId())
                    .name(user.getName())
                    .username(user.getUsername())
                    .roleType(user.getRole().name())
                    .build();
        } catch (Exception e) {
            System.out.println("An error occurs on finding user with id " + id);
            return null;
        }
    }
}
