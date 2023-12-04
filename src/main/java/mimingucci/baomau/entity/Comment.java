package mimingucci.baomau.entity;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "content", nullable = false)
    private String content;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "comment_like",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> agree=new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "comment_dislike",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> disagree=new ArrayList<>();
    @Column(name = "comment_time", nullable = false)
    private Date commenttime;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public Comment() {
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

    public List<User> getAgree() {
        return agree;
    }

    public void setAgree(List<User> agree) {
        this.agree = agree;
    }

    public List<User> getDisagree() {
        return disagree;
    }

    public void setDisagree(List<User> disagree) {
        this.disagree = disagree;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Date getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(Date commenttime) {
        this.commenttime = commenttime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void addAgree(User user){
        this.agree.add(user);
    }

    public void addDisagree(User user){
        this.disagree.add(user);
    }

    public void deleteAgree(int id){
        this.agree.remove(id);
    }

    public void deleteDisagree(int id){
        this.disagree.remove(id);
    }
}
