package origin.dto.project;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddProjectDto {
    private String name;
    private String description;
}
