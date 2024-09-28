package origin.service;

import com.google.cloud.storage.Blob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import origin.model.user.User;
import origin.repository.UserRepository;
import origin.service.firebase.FirebaseService;
import origin.utils.exception.ApiException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final FirebaseService firebaseService;
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->
                new ApiException("Пользователь с данным ID не найден", HttpStatus.BAD_REQUEST));
    }
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(()->
                new ApiException("Пользователь с данным ником не найден", HttpStatus.NOT_FOUND));
    }

    public List<User> getAll() {
        return userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getFullName)
                        .reversed())
                .collect(Collectors.toList());
    }

    public List<User> getAllByIdList(List<Long> userIdList) {
        return userRepository.findAllById(userIdList)
                .stream()
                .sorted(Comparator.comparing(User::getFullName).reversed())
                .collect(Collectors.toList());
    }


    public void save(User user){
        userRepository.save(user);
    }

    public void uploadImage(String username, MultipartFile file)  {
        User user = findUserByUsername(username);
        Blob blob = firebaseService.uploadFile(file);
        String link = blob.getMediaLink();
        user.getImage().add(link);
        save(user);
    }

}
