package mimingucci.baomau.entity;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "headline",length = 128, nullable = false)
    private String headline;

    @Column(name="content", length = 300, nullable = false)
    private String content;

    @Column(name = "rating")
    private int rating;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "review_like",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserDTO> agree=new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "review_dislike",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserDTO> disagree=new HashSet<>();

    @Column(name = "review_time")
    private Date reviewtime;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "user", nullable = false)
    private String user;

    public Review() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getReviewtime() {
        return reviewtime;
    }

    public void setReviewtime(Date reviewtime) {
        this.reviewtime = reviewtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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



    public void addAgree(UserDTO dto) {
        this.agree.add(dto);
        if(this.disagree.contains(dto)){
            this.disagree.remove(dto);
        }
    }

    public void addDisagree(UserDTO dto) {
        this.disagree.add(dto);
        if(this.agree.contains(dto)){
            this.agree.remove(dto);
        }
    }

    public void deleteLike(UserDTO dto){
        this.agree.remove(dto);
    }

    public void deleteDislike(UserDTO dto){
        this.disagree.remove(dto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return getAuthor().equals(review.getAuthor()) && getUser().equals(review.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthor(), getUser());
    }
}
