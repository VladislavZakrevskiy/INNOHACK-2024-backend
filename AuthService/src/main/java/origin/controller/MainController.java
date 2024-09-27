package origin.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import origin.dto.token.JwtDto;
import origin.dto.user.AuthUserDto;
import origin.dto.user.RegistrationUserDto;
import origin.service.AuthService;
import origin.service.JwtService;
import origin.service.RegistrationService;
import origin.service.UserService;
import origin.utils.validate.token.JwtValidate;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Auth")
public class MainController {
    private final AuthService authService;
    private final RegistrationService registrationService;
    private final JwtValidate jwtValidate;
    private final UserService userService;
    private final JwtService jwtService;


    @PostMapping("/auth")
    public JwtDto authentication(@Valid @RequestBody AuthUserDto authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public void registration(@Valid @RequestBody RegistrationUserDto registrationUserDto) {
        registrationService.registration(registrationUserDto);
    }

    @GetMapping("/check")
    public Map<String, String> checkJwt(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return jwtValidate.checkJwt(authHeader);
    }


    @PostMapping("/refresh")
    public JwtDto refreshAccessToken(@RequestBody JwtDto jwtDto) {
        return jwtService.refreshToken(jwtDto);
    }



}
