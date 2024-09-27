package steparrik.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import steparrik.dto.token.JwtDto;
import steparrik.dto.user.EditUserDto;
import steparrik.dto.user.EditUserKafkaDto;
import steparrik.model.user.User;
import steparrik.repository.UserRepository;
import steparrik.service.kafka.ProducerService;
import steparrik.utils.exception.ApiException;
import steparrik.utils.token.JwtTokenUtil;
import steparrik.utils.validate.user.UsernameValidate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UsernameValidate usernameValidate;
    private final ProducerService producerService;
    private final UserDetailService userDetailService;
    private final JwtTokenUtil jwtTokenUtil;

    public User findUserByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->
                new ApiException("Пользователь не найден", HttpStatus.NOT_FOUND));
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(()->
                new ApiException("Пользователь не найден", HttpStatus.NOT_FOUND));
    }

    public void save(User user){
        userRepository.save(user);
    }


    public JwtDto editUser(EditUserDto editUserDto, String username) {
        EditUserKafkaDto editUserKafkaDto = new EditUserKafkaDto();
        editUserKafkaDto.setOldUsername(username);
        User user = findUserByUsername(username);

        if(editUserDto.getUsername()!=null && !editUserDto.getUsername().isEmpty()){
            usernameValidate.checkUsername(editUserDto.getUsername());
            user.setUsername(editUserDto.getUsername());
            editUserKafkaDto.setUsername(editUserDto.getUsername());
        }
        if(editUserDto.getFullName()!=null && !editUserDto.getFullName().isEmpty()){
            editUserKafkaDto.setFullName(editUserDto.getFullName());
        }
        if(editUserDto.getPassword()!=null && !editUserDto.getPassword().isEmpty()){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encodePassword = bCryptPasswordEncoder.encode(editUserDto.getPassword());
            user.setPassword(encodePassword);
            editUserKafkaDto.setPassword(encodePassword);
        }

        producerService.edit(editUserKafkaDto);
        save(user);
        UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        return new JwtDto(accessToken, refreshToken);

    }

}
