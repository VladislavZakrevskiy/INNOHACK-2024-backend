package origin.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import origin.dto.user.RegistrationUserDto;
import origin.model.user.User;
import origin.service.UserService;
import origin.utils.mapper.user.RegistrationUserMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {
    private final UserService userService;
    private final RegistrationUserMapper registrationUserMapper;


    @KafkaListener(topics = "save-topic", groupId = "A")
    public void saveListener(RegistrationUserDto registrationUserDto) {
        User user = registrationUserMapper.toEntity(registrationUserDto);
        userService.save(user);
        log.info(registrationUserDto.getUsername() + " добавлен в users");
    }

}
