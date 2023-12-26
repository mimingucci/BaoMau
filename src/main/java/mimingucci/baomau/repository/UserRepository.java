package mimingucci.baomau.repository;

import mimingucci.baomau.entity.AuthenticationType;
import mimingucci.baomau.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByNickname(String nickname);

    @Modifying
    @Transactional
    @Query("delete from User i where i.nickname=?1")
    void deleteByNickname(String nickname);
    @Query("update User i set i.enabled=?2 where i.nickname=?1")
    @Modifying
    void updateEnabledStatus(String nickname, boolean enabled);

    @Query("update User i set i.password=?2 where i.nickname=?1")
    @Modifying
    void updatePassword(String nickname, String password);

    @Query("update User i set i.description=?2 where i.nickname=?1")
    @Modifying
    void updateDescription(String nickname, String description);

    @Query("update User i set i.authenticationtype=?2 where i.nickname=?1")
    @Modifying
    public void updateAuthenticationtype(String nickname, AuthenticationType type);

    List<User> findByFirstnameOrLastname(String firstname, String lastname);

    public User findByResetpasswordtoken(String token);

    @Query("select i from User i where i.verificationcode = ?1")
    public User findByVerificationcode(String code);

    @Query("SELECT u FROM User u WHERE u.enabled = true "
            + " ORDER BY u.rating DESC")
    public Page<User> listByRating(Pageable pageable);

    @Query(value = "SELECT * FROM user WHERE enabled = true AND "
            + "MATCH(nickname, firstname, lastname, email) AGAINST (?1)",
            nativeQuery = true)
    public Page<User> search(String keyword, Pageable pageable);
}
