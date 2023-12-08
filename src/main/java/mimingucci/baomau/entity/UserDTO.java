package mimingucci.baomau.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "userdto")
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nickname", length = 30, nullable = false, unique = true)
    private String nickname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserDTO(Integer id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public UserDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;
        return getId().equals(userDTO.getId()) && getNickname().equals(userDTO.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNickname());
    }
}
