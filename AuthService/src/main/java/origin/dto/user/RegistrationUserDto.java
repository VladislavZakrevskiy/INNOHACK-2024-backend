package origin.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistrationUserDto {
    @NotEmpty(message = "Пароль - обязательное поле")
    @Size(min = 5, message = "Минимальная длина пароля 5 символов")
    private String password;
    @NotEmpty(message = "Ник - обязательное поле")
    private String username;
    @NotEmpty(message = "Эмайл - обязательное поле")
    @Email(message = "Некорректная почта")
    private String email;
    @NotEmpty(message = "Полное имя не должно быть пустым")
    private String fullName;
}
