package mimingucci.baomau.entity;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nickname", length = 30, nullable = false, unique = true)
    private String nickname;
    @Column(name="email", length = 128, nullable = false, unique = true)
    private String email;
    @Column(name="password", length = 128, nullable = false)
    private String password;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;

    @Column(name = "photo")
    private String photo;

    @Column(name = "rating")
    private Double rating;

    @Column(name="description", length = 2000)
    private String description;
    private Boolean enabled;
    @Column(name = "createdname")
    private Date createdtime;

    @Column(name = "resetpasswordtoken", length = 30)
    private String resetpasswordtoken;

    @Column(name = "verificationcode", length = 64)
    private String verificationcode;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userid")
    private Set<Review> reviews=new HashSet<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "userid")
    private Set<Post> posts = new HashSet<>();

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "userid")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id")
            , inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles=new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "authenticationtype", length = 10)
    private AuthenticationType authenticationtype;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_likedcomment",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private Set<Comment> likedcomments=new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_dislikedcomment",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private Set<Comment> dislikedcomments=new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_likedpost",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> likedposts=new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_dislikedpost",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> dislikedposts=new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_likedreview",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private Set<Review> likedreviews=new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_dislikedreview",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private Set<Review> dislikedreviews=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "country_id")
    protected State state;

    public AuthenticationType getAuthenticationtype() {
        return authenticationtype;
    }

    public void setAuthenticationtype(AuthenticationType authenticationtype) {
        this.authenticationtype = authenticationtype;
    }

    public void addreview(Review review){
        this.reviews.add(review);
    }

    public void deletereview(Review review){
        if(this.reviews.contains(review)){
            this.reviews.remove(review);
        }
    }

    public String getResetpasswordtoken() {
        return resetpasswordtoken;
    }

    public void setResetpasswordtoken(String resetpasswordtoken) {
        this.resetpasswordtoken = resetpasswordtoken;
    }

    public String getVerificationcode() {
        return verificationcode;
    }

    public void setVerificationcode(String verificationcode) {
        this.verificationcode = verificationcode;
    }

    public void deleteallreview(){
        this.reviews.clear();
    }

    public User(String email, String password, String firstname, String lastname, String photo) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.photo = photo;
        this.createdtime=new Date();
    }

    public User() {
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public boolean hasRole(String roleName) {
        Iterator<Role> iterator = roles.iterator();

        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (role.getName().equals(roleName)) {
                return true;
            }
        }

        return false;
    }


    public String getFullname() {
        return firstname + " " + lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getNickname().equals(user.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNickname());
    }

    public void addPost(Post post){
        this.posts.add(post);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Comment> getLikedcomments() {
        return likedcomments;
    }

    public void setLikedcomments(Set<Comment> likedcomments) {
        this.likedcomments = likedcomments;
    }

    public Set<Comment> getDislikedcomments() {
        return dislikedcomments;
    }

    public void setDislikedcomments(Set<Comment> dislikedcomments) {
        this.dislikedcomments = dislikedcomments;
    }

    public Set<Post> getLikedposts() {
        return likedposts;
    }

    public void setLikedposts(Set<Post> likedposts) {
        this.likedposts = likedposts;
    }

    public Set<Post> getDislikedposts() {
        return dislikedposts;
    }

    public void setDislikedposts(Set<Post> dislikedposts) {
        this.dislikedposts = dislikedposts;
    }

    public Set<Review> getLikedreviews() {
        return likedreviews;
    }

    public void setLikedreviews(Set<Review> likedreviews) {
        this.likedreviews = likedreviews;
    }

    public Set<Review> getDislikedreviews() {
        return dislikedreviews;
    }

    public void setDislikedreviews(Set<Review> dislikedreviews) {
        this.dislikedreviews = dislikedreviews;
    }

    public void addLikedComment(Comment comment){
        this.dislikedcomments.remove(comment);
        this.likedcomments.add(comment);
    }

    public void addDislikedComment(Comment comment){
        this.likedcomments.remove(comment);
        this.dislikedcomments.add(comment);
    }

    public void addLikedPost(Post post){
        this.dislikedposts.remove(post);
        this.likedposts.add(post);
    }

    public void addDislikedPost(Post post){
        this.likedposts.remove(post);
        this.dislikedposts.add(post);
    }

    public void addLikedReview(Review review){
        this.dislikedreviews.remove(review);
        this.likedreviews.add(review);
    }

    public void addDislikedReview(Review review){
        this.likedreviews.remove(review);
        this.dislikedreviews.add(review);
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
