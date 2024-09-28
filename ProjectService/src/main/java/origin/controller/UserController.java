package origin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import origin.client.UserClient;
import origin.dto.user.ProfileUserDto;
import origin.model.project.Project;
import origin.model.space.Space;
import origin.service.ProjectService;
import origin.service.SpaceService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/user")
@Tag(name = "UserController")
public class UserController {

    private final UserClient userClient;
    private final ProjectService projectService;
    private final SpaceService spaceService;


    @GetMapping("/search")
    public List<ProfileUserDto> searchUsers(@RequestParam(required = false, name = "q", defaultValue = "") String q) {
        List<ProfileUserDto> profileUserDtoList = userClient.getAll();

        return profileUserDtoList
                .stream()
                .filter(profileDto ->
                        (profileDto != null) &&
                        (profileDto.getUsername().toLowerCase().contains(q.toLowerCase()) ||
                                profileDto.getFullName().toLowerCase().contains(q.toLowerCase()))
                )
                .collect(Collectors.toList());
    }

    @GetMapping("/search/byid/{userId}")
    public ProfileUserDto searchUserById(@PathVariable long userId) {
        return userClient.getUserById(userId);
    }

    @GetMapping("/search/byproj/{projectId}")
    public List<ProfileUserDto> searchUsersProject(@PathVariable long projectId) {
        Project project = projectService.getById(projectId);

        return project.getMembersId().size() > 0 ? userClient.getAllUsersByIdList(project.getMembersId()) : new ArrayList<>();
    }

    @GetMapping("/search/byspace/{spaceId}")
    public List<ProfileUserDto> searchUsersSpace(@PathVariable long spaceId) {
        Space space = spaceService.getById(spaceId);

        return space.getMembersId().size() > 0 ? userClient.getAllUsersByIdList(space.getMembersId()) : new ArrayList<>();
    }
}
