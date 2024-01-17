package ro.tuc.ds2020.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.entities.dtos.UserRequest;
import ro.tuc.ds2020.services.UserService;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/save")
    public ResponseEntity<Boolean> saveUser(@RequestBody User userRequest) {
        boolean userResponse = userService.saveUser(userRequest);
        if (userResponse) {
            log.info("User with username {} was inserted in db", userRequest.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        } else {
            log.error("User with username {} already exist", userRequest.getUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @DeleteMapping (value = "/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") UUID id) {
        boolean deleteResponse = userService.deleteUser(id);
        if (deleteResponse) {
            log.info("User with id {} was deleted successfully", id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } else {
            log.info("An error occurs on deletion of user with id {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody User user){
        boolean userResponse = userService.updateUser(user);
        System.out.println(user.getId());
        if (userResponse) {
            log.info("User with id {} was updated in db", user.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        } else {
            log.error("An error occurs on update user with id {} ", user.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
}
