package chapterSeven.lifeCycle;

import lombok.*;
import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@Data
@EntityListeners({UserAccountListener.class})
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    @Transient
    String password;
    Integer salt;
    Integer passwordHash;

    public boolean validPassword(String newPass) {
        return newPass.hashCode() * salt == getPasswordHash();
    }
}
