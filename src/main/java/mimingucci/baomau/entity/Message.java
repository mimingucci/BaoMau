package mimingucci.baomau.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 2000, nullable = false)
    private String content;
    private Date time;
    @Column(nullable = false)
    private String recipient;
    @Column(nullable = false)
    private String author;

    private Boolean seen;

    public Message() {
        this.time=new Date();
        seen=false;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message message)) return false;
        return getContent().equals(message.getContent()) && getTime().equals(message.getTime()) && getRecipient().equals(message.getRecipient()) && getAuthor().equals(message.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent(), getTime(), getRecipient(), getAuthor());
    }
}
