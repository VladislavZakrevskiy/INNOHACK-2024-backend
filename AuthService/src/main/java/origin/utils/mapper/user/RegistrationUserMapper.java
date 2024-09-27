package origin.utils.mapper.user;

import org.springframework.stereotype.Component;
import origin.dto.user.RegistrationUserDto;
import origin.model.user.User;

@Component
public class RegistrationUserMapper {
    public User toEntity(RegistrationUserDto d) {
        if ( d == null ) {
            return null;
        }

        User user = new User();

        user.setPassword( d.getPassword() );
        user.setUsername( d.getUsername() );
        user.setEmail(d.getEmail());

        return user;
    }

    public RegistrationUserDto toDto(User e) {
        if ( e == null ) {
            return null;
        }

        RegistrationUserDto registrationUserDto = new RegistrationUserDto();

        registrationUserDto.setPassword( e.getPassword() );
        registrationUserDto.setUsername( e.getUsername() );
        registrationUserDto.setEmail(e.getEmail());
        return registrationUserDto;
    }
}
