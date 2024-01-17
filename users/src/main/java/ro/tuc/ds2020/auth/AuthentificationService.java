package ro.tuc.ds2020.auth;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.ResourceAccessException;
import ro.tuc.ds2020.config.JwtService;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.CustomException;
import ro.tuc.ds2020.entities.dtos.AuthenticationResponse;
import ro.tuc.ds2020.entities.dtos.CredentialsRequest;
import ro.tuc.ds2020.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthentificationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(CredentialsRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );
        var user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new ResourceAccessException("User with provided username doesn't exist. AuthentificateService"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .uuid(user.getId())
                .role(user.getRole().name())
                .expirationDate(jwtService.extractExpiration(jwtToken))
                .build();
    }
}
