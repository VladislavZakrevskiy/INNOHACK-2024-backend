package origin.dto.user;

import lombok.*;

import java.util.List;

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

    private List<String> image;

}
