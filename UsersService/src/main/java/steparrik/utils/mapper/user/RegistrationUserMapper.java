package steparrik.utils.mapper.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import steparrik.dto.user.RegistrationUserDto;
import steparrik.model.user.User;

@Component
public class RegistrationUserMapper {
    public User toEntity(RegistrationUserDto registrationUserDto){
        User user = new User();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(registrationUserDto.getPassword()));
        user.setUsername(registrationUserDto.getUsername());
        user.setFullName(registrationUserDto.getFullName());
        user.setPhoneNumber(registrationUserDto.getPhoneNumber());
        return user;
    }
}
