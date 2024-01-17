package ro.tuc.ds2020.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.tuc.ds2020.entities.dtos.AuthenticationResponse;
import ro.tuc.ds2020.entities.dtos.CredentialsRequest;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthentificationService authentificationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody CredentialsRequest authRequest) {
        return ResponseEntity.ok(authentificationService.authenticate(authRequest));
    }
}
