package steparrik.utils.validate.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import steparrik.dto.user.RegistrationUserDto;
import steparrik.service.UserService;
import steparrik.utils.exception.ApiException;

@Component
@RequiredArgsConstructor
public class RegistrationDataValidate {
    private final UserService userService;
    private final UsernameValidate usernameValidate;

    public void validateRegistrationDate(RegistrationUserDto registrationUserDto){
        if (!checkUserOnUsername(registrationUserDto.getUsername())) {
            throw new ApiException("Данный ник занят другим пользователем", HttpStatus.BAD_REQUEST);
        }

        if (!checkUserOnNumber(registrationUserDto.getPhoneNumber())) {
            throw new ApiException("Данный номер занят другим пользователем", HttpStatus.BAD_REQUEST);
        }

        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new ApiException ("Пароли не совпадают", HttpStatus.BAD_REQUEST);
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

    private boolean checkUserOnNumber(String phoneNumber) {
        try {
            userService.findUserByPhoneNumber(phoneNumber);
            return false;
        } catch (ApiException e) {
            return true;
        }
    }

}
