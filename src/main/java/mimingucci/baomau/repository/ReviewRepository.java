package mimingucci.baomau.repository;

import mimingucci.baomau.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("update Review i set i.headline=?2, i.content=?3 where i.id=?1")
    @Modifying
    void updateReview(int reviewid, String headline, String content);

    @Query("select i from Review i where i.author=?1 and i.user=?2")
    Review findByAuthorAndUser(String author, String user);
}
