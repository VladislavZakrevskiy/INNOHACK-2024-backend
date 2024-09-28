package origin.model.space;

import jakarta.persistence.*;
import lombok.*;
import origin.model.project.Project;
import origin.model.status.Status;
import origin.model.task.Task;

import java.util.List;

@Entity
@Table(name = "space")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private Long OwnerId;

    @ElementCollection
    private List<Long> membersId;


    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Status> status;
}
