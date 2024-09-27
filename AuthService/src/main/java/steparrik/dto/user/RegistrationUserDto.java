package steparrik.dto.user;

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
    @NotEmpty(message = "Повторение пароля - обязательное поле")
    @Size(min = 5, message = "Минимальная длина пароля 5 символов")
    private String confirmPassword;
    @NotEmpty(message = "Ник - обязательное поле")
    private String username;
    @Pattern(regexp = "^8\\d{10}$", message = "Неверный формат, номер должен соответстовать 8xxxxxxxxxx")
    private String phoneNumber;
    @NotEmpty(message = "Полное имя не должно быть пустым")
    private String fullName;
}
