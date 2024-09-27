package origin.utils.mapper.user;

import org.springframework.stereotype.Component;
import origin.dto.user.ProfileUserDto;
import origin.model.user.User;

@Component
public class ProfileUserMapper {

    public ProfileUserDto toDto(User user){
        ProfileUserDto profileUserDto = new ProfileUserDto();
        profileUserDto.setId(user.getId());
        profileUserDto.setUsername(user.getUsername());
        profileUserDto.setFullName(user.getFullName());
        profileUserDto.setEmail(user.getEmail());
        profileUserDto.setImage(user.getImage());
        return profileUserDto;
    }
}
