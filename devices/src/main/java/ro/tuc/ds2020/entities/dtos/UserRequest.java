package ro.tuc.ds2020.entities.dtos;

import lombok.*;
import ro.tuc.ds2020.entities.RoleType;

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


    @Override
    public String toString() {
        return "UserRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roleType='" + roleType + '\'' +
                '}';
    }
}
