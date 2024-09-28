package origin.model.status;


import jakarta.persistence.*;
import lombok.*;
import origin.model.project.Project;
import origin.model.space.Space;
import origin.model.task.Task;

import java.util.List;

@Entity
@Table(name = "status")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "space_id", referencedColumnName = "id")
    private Space space;
}
