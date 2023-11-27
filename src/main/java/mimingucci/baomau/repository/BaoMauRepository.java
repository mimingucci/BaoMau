package mimingucci.baomau.repository;

import mimingucci.baomau.entity.BaoMau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BaoMauRepository extends JpaRepository<BaoMau, Integer> {
    BaoMau findByEmail(String email);

    @Query("update BaoMau i set i.enabled=?2 where i.id=?1")
    @Modifying
    void updateEnabledStatus(int id, boolean enabled);

    @Query("update BaoMau i set i.password=?2 where i.id=?1")
    @Modifying
    void updatePassword(int id, String password);

    @Query("update BaoMau i set i.description=?2 where i.id=?1")
    @Modifying
    void updateDescription(int id, String description);
}
