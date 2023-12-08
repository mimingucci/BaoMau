package mimingucci.baomau.repository;

import mimingucci.baomau.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("update Post i set i.headline=?2, i.content=?3 where i.id=?1")
    @Modifying
    public void updatePost(int id, String headline, String content);
}
