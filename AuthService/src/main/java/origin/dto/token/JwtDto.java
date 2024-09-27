package origin.dto.token;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtDto {
    private String accessToken;
    private String refreshToken;
}
