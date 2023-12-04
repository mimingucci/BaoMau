package mimingucci.baomau.repository;

import mimingucci.baomau.entity.AuthenticationType;
import mimingucci.baomau.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("update User i set i.enabled=?2 where i.id=?1")
    @Modifying
    void updateEnabledStatus(int id, boolean enabled);

    @Query("update User i set i.password=?2 where i.id=?1")
    @Modifying
    void updatePassword(int id, String password);

    @Query("update User i set i.description=?2 where i.id=?1")
    @Modifying
    void updateDescription(int id, String description);

    @Query("update User i set i.authenticationtype=?2 where i.id=?1")
    @Modifying
    public void updateAuthenticationtype(Integer userId, AuthenticationType type);

    List<User> findByFirstnameOrLastname(String firstname, String lastname);

    public User findByResetpasswordtoken(String token);

    @Query("select i from User i where i.verificationcode = ?1")
    public User findByVerificationcode(String code);
}
