package steparrik.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@Entity
@Table(name = "users")
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String password;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    @Column(unique = true)
    private String phoneNumber;


    public User(String password, String username) {
        this.password = password;
        this.username = username;
    }
}

