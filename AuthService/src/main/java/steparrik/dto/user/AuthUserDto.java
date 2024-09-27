package steparrik.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthUserDto {
    @NotEmpty(message = "Ник - обязательное поле")
    private String username;
    @Size(min = 5, message = "Минимальная длина пароля 5 символов")
    private String password;
}
