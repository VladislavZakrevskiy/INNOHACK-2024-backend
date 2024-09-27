package steparrik.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import steparrik.dto.token.JwtDto;
import steparrik.dto.user.AuthUserDto;
import steparrik.utils.exception.ApiException;
import steparrik.utils.token.JwtTokenUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDetailService userDetailService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public JwtDto createAuthToken(AuthUserDto authRequest) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword()));
        } catch (
                BadCredentialsException e) {
            throw new ApiException("Неверное имя пользователя или пароль", HttpStatus.BAD_REQUEST);
        }
        UserDetails userDetails = userDetailService.loadUserByUsername(authRequest.getUsername());
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        return new JwtDto(accessToken, refreshToken);
    }
}
