package mimingucci.baomau.repository;

import mimingucci.baomau.entity.User;
import mimingucci.baomau.entity.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserDTORepository extends JpaRepository<UserDTO, Integer> {
    UserDTO findByNickname(String nickname);

    @Modifying
    @Transactional
    @Query("delete from UserDTO i where i.nickname=?1")
    void deleteByNickname(String nickname);
}
