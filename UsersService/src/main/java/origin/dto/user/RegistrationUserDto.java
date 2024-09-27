package origin.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistrationUserDto {
    private String password;
    private String confirmPassword;
    private String username;
    private String email;
    private String fullName;
}
