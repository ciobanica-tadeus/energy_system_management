package ro.tuc.ds2020.entities.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private UUID id;
    private String name;
    private String username;
    private String password;
    private String roleType;
}
