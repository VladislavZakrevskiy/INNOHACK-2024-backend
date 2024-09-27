package steparrik.dto.user;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EditUserDto {
    private String username;
    private String fullName;
    @Size(min = 5, message = "Минимальная длина пароля 5 символов")
    private String password;
}
