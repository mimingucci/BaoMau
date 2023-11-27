package mimingucci.baomau.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(length = 128, nullable = false)
    private String headline;

    @Column(length = 300, nullable = false)
    private String comment;

    private Set<Integer> agree=new HashSet<>();

    private Set<Integer> disagree=new HashSet<>();

    @Column(name = "posted_time", nullable = false)
    private Date postedtime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<Comment> comments=new ArrayList<>();

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<Integer> getAgree() {
        return agree;
    }

    public void setAgree(Set<Integer> agree) {
        this.agree = agree;
    }

    public Set<Integer> getDisagree() {
        return disagree;
    }

    public void setDisagree(Set<Integer> disagree) {
        this.disagree = disagree;
    }

    public Date getPostedtime() {
        return postedtime;
    }

    public void setPostedtime(Date postedtime) {
        this.postedtime = postedtime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
