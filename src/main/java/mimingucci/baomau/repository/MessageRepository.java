package mimingucci.baomau.repository;

import mimingucci.baomau.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("select m from Message m where m.author=?1 or m.recipient=?1")
    List<Message> findByNickname(String nickname);

    @Query("select m from Message m where m.author=?1")
    List<Message> findByAuthor(String nickname);

    @Query("select m from Message m where m.recipient=?1")
    List<Message> findByRecipient(String nickname);

    @Query("select m from Message m where (m.author=?1 and m.recipient=?2) or (m.author=?2 and m.recipient=?1)")
    List<Message> findByAuthorAndRecipient(String author, String recipient);

    @Query("update Message m set m.seen=?3 where (m.author=?1 and m.recipient=?2) or (m.author=?2 and m.recipient=?1)")
    @Transactional
    @Modifying
    public void updateSeenStatus(String author, String recipient, boolean status);
}
