package steparrik.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EditUserKafkaDto {
    private String username;

    private String fullName;

    private String password;

    private String oldUsername;
}
