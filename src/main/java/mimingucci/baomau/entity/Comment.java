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
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "comment_like",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserDTO> agree=new HashSet<>();
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "comment_dislike",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserDTO> disagree=new HashSet<>();
    @Column(name = "comment_time")
    private Date commenttime;

    private String author;

    private Integer post;

    public Integer getPost() {
        return post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return getId().equals(comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void setPost(Integer post) {
        this.post = post;
    }

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

    public void setContent(String content) {
        this.content = content;
    }


    public Date getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(Date commenttime) {
        this.commenttime = commenttime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void addAgree(UserDTO user){
        this.agree.add(user);
    }

    public void addDisagree(UserDTO user){
        this.disagree.add(user);
    }

    public void deleteAgree(int id){
        this.agree.remove(id);
    }

    public void deleteDisagree(int id){
        this.disagree.remove(id);
    }

    public Set<UserDTO> getAgree() {
        return agree;
    }

    public void setAgree(Set<UserDTO> agree) {
        this.agree = agree;
    }

    public Set<UserDTO> getDisagree() {
        return disagree;
    }

    public void setDisagree(Set<UserDTO> disagree) {
        this.disagree = disagree;
    }
}
