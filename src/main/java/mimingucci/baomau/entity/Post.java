package mimingucci.baomau.entity;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 128, nullable = false)
    private String headline;

    @Column(length = 300, nullable = false)
    private String content;


    private String author;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "post_like",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserDTO> agree=new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "post_dislike",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserDTO> disagree=new HashSet<>();

    @Column(name = "posted_time")
    private Date postedtime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private Set<Comment> comments=new HashSet<>();


    public Post() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostedtime() {
        return postedtime;
    }

    public void setPostedtime(Date postedtime) {
        this.postedtime = postedtime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return getId().equals(post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
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
