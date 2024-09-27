package steparrik.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileUserDto  {
    private Long id;

    private String username;

    private String phoneNumber;

    private String fullName;
}
