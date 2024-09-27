package origin.utils.mapper.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import origin.dto.user.RegistrationUserDto;
import origin.model.user.User;

@Component
public class RegistrationUserMapper {
    public User toEntity(RegistrationUserDto registrationUserDto){
        User user = new User();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(registrationUserDto.getPassword()));
        user.setUsername(registrationUserDto.getUsername());
        user.setFullName(registrationUserDto.getFullName());
        user.setEmail(registrationUserDto.getEmail());
        return user;
    }
}
