package mimingucci.baomau.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "agree")
    private Set<Integer> agree;
    @Column(name = "disagree")
    private Set<Integer> disagree;
    @Column(name = "comment_time", nullable = false)
    private Date commenttime;
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer author;

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

    public Date getCommenttime() {
        return commenttime;
    }

    public void setCommenttime(Date commenttime) {
        this.commenttime = commenttime;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
    }

    public void addagree(int id){
        this.agree.add(id);
    }

    public void adddisagree(int id){
        this.disagree.add(id);
    }

    public void deleteagree(int id){
        this.agree.remove(id);
    }

    public void deletedisagree(int id){
        this.disagree.remove(id);
    }
}
