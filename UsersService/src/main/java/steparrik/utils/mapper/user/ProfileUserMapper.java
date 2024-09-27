package steparrik.utils.mapper.user;

import org.springframework.stereotype.Component;
import steparrik.dto.user.ProfileUserDto;
import steparrik.model.user.User;

@Component
public class ProfileUserMapper {

    public ProfileUserDto toDto(User user){
        ProfileUserDto profileUserDto = new ProfileUserDto();
        profileUserDto.setId(user.getId());
        profileUserDto.setUsername(user.getUsername());
        profileUserDto.setFullName(user.getFullName());
        profileUserDto.setPhoneNumber(user.getPhoneNumber());

        return profileUserDto;
    }
}
