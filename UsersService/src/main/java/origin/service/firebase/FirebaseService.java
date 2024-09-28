package origin.service.firebase;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import origin.utils.exception.ApiException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FirebaseService {
    public Blob uploadFile(MultipartFile file) {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob;

        if(file==null || file.isEmpty()){
            throw new ApiException("Файл не может быть пустым", HttpStatus.BAD_REQUEST);
        }

        try {
            blob = bucket.create(file.getOriginalFilename(), file.getBytes(), file.getContentType());
        } catch (IOException e) {
            throw new ApiException("Ошибка при преобразовнии файла", HttpStatus.BAD_REQUEST);
        }

        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        return blob;
    }

}
