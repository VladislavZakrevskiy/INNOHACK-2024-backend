package steparrik.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import steparrik.dto.token.JwtDto;
import steparrik.dto.user.AuthUserDto;
import steparrik.dto.user.EditUserDto;
import steparrik.dto.user.RegistrationUserDto;
import steparrik.service.AuthService;
import steparrik.service.JwtService;
import steparrik.service.RegistrationService;
import steparrik.service.UserService;
import steparrik.utils.validate.token.JwtValidate;

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


    @PutMapping("/editUser")
    public JwtDto editProfile(@Valid@RequestBody EditUserDto editUserDto, @RequestHeader(value = "X-Username", required = true) String username) {
        return userService.editUser(editUserDto, username);
    }

    @PostMapping("/refresh")
    public JwtDto refreshAccessToken(@RequestBody JwtDto jwtDto) {
        return jwtService.refreshToken(jwtDto);
    }



}
