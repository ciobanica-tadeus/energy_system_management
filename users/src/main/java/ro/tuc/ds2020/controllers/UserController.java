package ro.tuc.ds2020.controllers;

import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.dtos.ListUserResponse;
import ro.tuc.ds2020.entities.dtos.UserRequest;
import ro.tuc.ds2020.entities.dtos.UserResponse;
import ro.tuc.ds2020.repositories.UserRepository;
import ro.tuc.ds2020.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping(value = "/save")
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.saveUser(userRequest);
        if (userResponse != null) {
            log.info("User with username {} was inserted in db", userRequest.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } else {
            log.error("User with username {} already exist", userRequest.getUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") UUID id) {
        boolean deleteResponse = userService.deleteUser(id);
        if(deleteResponse){
            log.info("User with id {} was deleted successfully", id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }else{
            log.info("An error occurs on deletion of user with id {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest){
        UserResponse userResponse = userService.updateUser(userRequest);

        if (userResponse != null) {
            log.info("User with id {} was updated in db", userRequest.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } else {
            log.error("An error occurs on update user with id {} ", userRequest.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<ListUserResponse> getUsers() {
        ListUserResponse usersList = userService.getAllUsers();
        if (usersList.getUsersList().isEmpty()) {
            log.info("The users table is empty");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usersList);
    }

    @GetMapping(value = "/getUser/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") UUID id) {
        UserResponse userResponse = userService.getUserById(id);
        if(userResponse != null){
            log.info("Username with id {} was successfully find", id);
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        }else{
            log.info("An error occurs on finding user with id {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
