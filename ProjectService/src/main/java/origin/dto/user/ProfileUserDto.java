package origin.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileUserDto {
    private Long id;

    private String username;

    private String email;

    private String fullName;

    private String image;

}
