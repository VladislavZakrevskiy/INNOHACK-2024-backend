package origin.utils.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExceptionEntity {
    private String message;
    private LocalDateTime localDateTime;
}

