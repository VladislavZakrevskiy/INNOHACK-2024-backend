package steparrik.utils.validate.token;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import steparrik.utils.token.JwtTokenUtil;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtValidate {
    private final JwtTokenUtil jwtTokenUtil;

    public Map<String, String> checkJwt(String authHeader) {
        String token = authHeader.substring(7);
        Map<String, String> userDetails = new HashMap<>();
        try {
            String username = jwtTokenUtil.getUsername(token);
            userDetails.put("username", username);
        } catch (ExpiredJwtException e) {
            userDetails.put("error", "Время использования токена истекло");
        } catch (SignatureException | MalformedJwtException e) {
            userDetails.put("error", "Неверная запись токена");
        }
        return userDetails;
    }
}
