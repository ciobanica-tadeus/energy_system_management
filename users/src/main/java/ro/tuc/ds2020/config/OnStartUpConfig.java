package ro.tuc.ds2020.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.entities.RoleType;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OnStartUpConfig {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void atStart(){
        User admin = User
                .builder()
                .name("Ciobanica Tadeus")
                .username("tadeus")
                .password(passwordEncoder.encode("12345"))
                .role(RoleType.valueOf("ADMIN"))
                .build();
        Optional<User> userOptional1 = userRepository.findByUsername(admin.getUsername());
        if(userOptional1.isEmpty()){
            userRepository.save(admin);
        }
        User client1 = User
                .builder()
                .name("Vasile Gheorghe")
                .username("test123")
                .password(passwordEncoder.encode("12345"))
                .role(RoleType.CLIENT)
                .build();
        Optional<User> userOptional2 = userRepository.findByUsername(client1.getUsername());
        if(userOptional2.isEmpty()){
            userRepository.save(client1);
        }
    }
}


