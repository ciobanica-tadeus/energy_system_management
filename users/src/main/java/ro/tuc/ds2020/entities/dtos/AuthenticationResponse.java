package ro.tuc.ds2020.entities.dtos;

import lombok.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private UUID uuid;
    private String accessToken;
    private String role;
    private Date expirationDate;
}
