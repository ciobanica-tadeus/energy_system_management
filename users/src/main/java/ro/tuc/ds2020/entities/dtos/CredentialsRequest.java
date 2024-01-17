package ro.tuc.ds2020.entities.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialsRequest {
    private String username;
    private String password;
}
