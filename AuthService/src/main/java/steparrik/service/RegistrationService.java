package steparrik.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import steparrik.dto.user.RegistrationUserDto;
import steparrik.model.user.User;
import steparrik.service.kafka.ProducerService;
import steparrik.utils.mapper.user.RegistrationUserMapper;
import steparrik.utils.validate.user.RegistrationDataValidate;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final UserService userService;
    private final RegistrationUserMapper registrationUserMapper;
    private final RegistrationDataValidate registrationDataValidate;
    private final ProducerService producerService;

    @Transactional
    public void registration(RegistrationUserDto registrationUserDto) {
        registrationDataValidate.validateRegistrationDate(registrationUserDto);
        producerService.save(registrationUserDto);
        User user = registrationUserMapper.toEntity(registrationUserDto);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(registrationUserDto.getPassword()));
        userService.save(user);
        log.info(String.format("user %s was be  registered", user.getUsername()));
    }

}
