package origin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import origin.ProjectServiceApplication;
import origin.client.UserClient;
import origin.dto.project.AddProjectDto;
import origin.dto.project.GetProjectDto;
import origin.dto.user.ProfileUserDto;
import origin.model.project.Project;
import origin.service.ProjectService;
import origin.utils.mapper.ProjectMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Tag(name = "ProjectService")
public class MainController {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final UserClient userClient;
    @GetMapping
    public List<GetProjectDto> getAllProjects(@RequestHeader(value = "X-Username") String username) {
        return projectService.getAllProject().stream().map(projectMapper::toDto).collect(Collectors.toList());
    }



    @PostMapping
    public GetProjectDto createProject(@RequestHeader(value = "X-Username") String usernameOwner,
                                       @RequestBody AddProjectDto addProjectDto){
        ProfileUserDto profileUserDto = userClient.getUserByUsername(usernameOwner);
        Project project = new Project();
        project.setName(addProjectDto.getName());
        project.setMembersId(Collections.singletonList(profileUserDto.getId()));
        project.setOwnerId(profileUserDto.getId());
        project.setSpaces(new ArrayList<>());

        return projectMapper.toDto(projectService.save(project));
    }
//
//    @GetMapping("/{id}")
//    public ChatForMenuChatsDto definiteChat(@PathVariable long id, @RequestHeader(value = "X-Username") String username){
//        ProfileUserDto profileUserDto = userClient.getUserByUsername(username);
//        Chat chat = chatService.getDefiniteChat(id, profileUserDto);
//        ChatForCorrespondDto chatForCorrespondDto = chatForCorrespondMapper.toDto(chat);
//        return chatService.chooseDialogName(profileUserDto, chatForCorrespondDto, chat);
//    }
//
//    @GetMapping("/{id}/participants")
//    public List<ProfileUserDto> allParticipants(@PathVariable long id, @RequestHeader(value = "X-Username") String username){
//        return chatService.getParticipants(id);
//    }
//
//    @PostMapping("/{id}")
//    public MessageDTO sendMessage(@PathVariable long id, @Valid @RequestBody MessageDTO messageDTO, @RequestHeader(value = "X-Username") String username){
//        ProfileUserDto sender = userClient.getUserByUsername(username);
//        Chat chat = chatService.getDefiniteChat(id, sender);
//
//        return messageService.sendMessage(id, messageDTO, sender, chat);
//    }
//
//    @PutMapping("/{id}")
//    public void addNewParticipant(@PathVariable long id,@RequestParam(required = false) String username,
//                                               @RequestParam(required = false) String phoneNumber,
//                                               @RequestHeader(value = "X-Username") String usernameOwner){
//        ProfileUserDto principalUser = userClient.getUserByUsername(usernameOwner);
//        chatService.addParticipant(id, username, phoneNumber, principalUser);
//    }


}
