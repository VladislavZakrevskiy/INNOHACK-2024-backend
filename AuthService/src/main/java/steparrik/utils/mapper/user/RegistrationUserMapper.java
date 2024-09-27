package steparrik.utils.mapper.user;

import org.springframework.stereotype.Component;
import steparrik.dto.user.RegistrationUserDto;
import steparrik.model.user.User;

@Component
public class RegistrationUserMapper {
    public User toEntity(RegistrationUserDto d) {
        if ( d == null ) {
            return null;
        }

        User user = new User();

        user.setPassword( d.getPassword() );
        user.setUsername( d.getUsername() );
        user.setPhoneNumber( d.getPhoneNumber() );

        return user;
    }

    public RegistrationUserDto toDto(User e) {
        if ( e == null ) {
            return null;
        }

        RegistrationUserDto registrationUserDto = new RegistrationUserDto();

        registrationUserDto.setPassword( e.getPassword() );
        registrationUserDto.setUsername( e.getUsername() );
        registrationUserDto.setPhoneNumber( e.getPhoneNumber() );

        return registrationUserDto;
    }
}
