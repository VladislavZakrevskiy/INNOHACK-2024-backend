package origin.utils.validate.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import origin.dto.user.RegistrationUserDto;
import origin.service.UserService;
import origin.utils.exception.ApiException;

@Component
@RequiredArgsConstructor
public class RegistrationDataValidate {
    private final UserService userService;
    private final UsernameValidate usernameValidate;

    public void validateRegistrationDate(RegistrationUserDto registrationUserDto){
        if (!checkUserOnUsername(registrationUserDto.getUsername())) {
            throw new ApiException("Данный ник занят другим пользователем", HttpStatus.BAD_REQUEST);
        }

        if (!checkUserOnEmail(registrationUserDto.getEmail())) {
            throw new ApiException("Данная почта занята другим пользователем", HttpStatus.BAD_REQUEST);
        }
        usernameValidate.checkUsername(registrationUserDto.getUsername());
    }

    private boolean checkUserOnUsername(String username) {
        try {
            userService.findUserByUsername(username);
            return false;
        } catch (ApiException e) {
            return true;
        }
    }

    private boolean checkUserOnEmail(String email) {
        try {
            userService.findUserByEmail(email);
            return false;
        } catch (ApiException e) {
            return true;
        }
    }

}
