package mimingucci.baomau.repository;

import mimingucci.baomau.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("update Comment i set i.content=?2 where i.id=?1")
    public void updateComment(int id, String content);
}
