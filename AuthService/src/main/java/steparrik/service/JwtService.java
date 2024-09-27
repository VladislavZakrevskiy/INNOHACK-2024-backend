package steparrik.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import steparrik.dto.token.JwtDto;
import steparrik.model.user.User;
import steparrik.utils.exception.ApiException;
import steparrik.utils.token.JwtTokenUtil;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final UserDetailService userDetailService;

    public JwtDto refreshToken(JwtDto jwtDto){
        if(isTokenExpired(jwtDto.getRefreshToken())){
            throw new ApiException("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        userService.findUserByUsername(jwtTokenUtil.getUsername(jwtDto.getRefreshToken()));

        UserDetails userDetails = userDetailService.loadUserByUsername(jwtTokenUtil.getUsername(jwtDto.getRefreshToken()));
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        return new JwtDto(accessToken, refreshToken);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = jwtTokenUtil.getAllClaimsFromToken(token).getExpiration();
        return expirationDate.before(new Date());
    }


}
