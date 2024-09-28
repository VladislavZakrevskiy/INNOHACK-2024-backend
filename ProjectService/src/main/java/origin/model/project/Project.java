package origin.model.project;

import jakarta.persistence.*;
import lombok.*;
import origin.model.space.Space;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "project")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String image;

    private Long OwnerId;

    @ElementCollection
    private List<Long> membersId;

    @OneToMany(mappedBy = "project")
    private List<Space> spaces;

}