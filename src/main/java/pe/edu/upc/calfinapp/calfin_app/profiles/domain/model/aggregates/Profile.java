package pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pe.edu.upc.calfinapp.calfin_app.profiles.domain.model.valueobjects.UserId;
import pe.edu.upc.calfinapp.calfin_app.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private Integer age;

    @Column(nullable = true, unique = true)
    private String email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false, unique = true))
    private UserId userId;

    public Profile(String username, String password, UserId userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public void edit(String email, Integer age) {
        this.email = email;
        this.age = age;
    }
}