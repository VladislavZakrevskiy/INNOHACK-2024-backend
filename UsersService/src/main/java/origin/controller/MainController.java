package origin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import origin.dto.user.ProfileUserDto;
import origin.model.user.User;
import origin.service.UserService;
import origin.utils.mapper.user.ProfileUserMapper;

@RestController
@RequiredArgsConstructor
@Tag(name = "UserService")
public class MainController {
    private final UserService userService;
    private final ProfileUserMapper profileUserMapper;

    @GetMapping("/profile")
    public ProfileUserDto profile(@RequestHeader(value = "X-Username") String principalUsername) {
        User user;
        user = userService.findUserByUsername(principalUsername);
        return profileUserMapper.toDto(user);
    }


    @GetMapping("/profile/{id}")
    public ProfileUserDto profile(@PathVariable Long id) {
        User user =  userService.findById(id);
        return profileUserMapper.toDto(user);
    }

    @PostMapping("/profile/uploadImage")
    public ProfileUserDto uploadIMge(@RequestHeader(value = "X-Username") String principalUsername,
                                     @RequestParam(required = true)MultipartFile file) {
        userService.uploadImage(principalUsername, file);
        return profileUserMapper.toDto(userService.findUserByUsername(principalUsername));
    }



}
