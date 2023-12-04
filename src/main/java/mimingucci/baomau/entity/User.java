package mimingucci.baomau.entity;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 128, nullable = false, unique = true)
    private String email;
    @Column(length = 128, nullable = false)
    private String password;
    @Column(name = "first_name", nullable = false)
    private String firstname;
    @Column(name = "last_name", nullable = false)
    private String lastname;
    private String photo;
    @Column(length = 2000)
    private String description;
    private Boolean enabled;
    @Column(name = "created_name")
    private Date createdtime;

    @Column(name = "reset_password_token", length = 30)
    private String resetpasswordtoken;

    @Column(name = "verification_code", length = 64)
    private String verificationcode;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Review> reviews=new ArrayList<>();

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id")
            , inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles=new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_type", length = 10)
    private AuthenticationType authenticationtype;

    @ManyToMany(mappedBy = "agree")
    private List<Post> likedposts = new ArrayList<>();

    @ManyToMany(mappedBy = "disagree")
    private List<Post> dislikedposts = new ArrayList<>();

    @ManyToMany(mappedBy = "agree")
    private List<Comment> likedcomments = new ArrayList<>();

    @ManyToMany(mappedBy = "disagree")
    private List<Comment> dislikedcomments = new ArrayList<>();

    @ManyToMany(mappedBy = "agree")
    private List<Review> likedreviews = new ArrayList<>();

    @ManyToMany(mappedBy = "disagree")
    private List<Review> dislikedreviews = new ArrayList<>();

    public List<Comment> getLikedcomments() {
        return likedcomments;
    }

    public void setLikedcomments(List<Comment> likedcomments) {
        this.likedcomments = likedcomments;
    }

    public List<Comment> getDislikedcomments() {
        return dislikedcomments;
    }

    public void setDislikedcomments(List<Comment> dislikedcomments) {
        this.dislikedcomments = dislikedcomments;
    }

    public List<Post> getLikedposts() {
        return likedposts;
    }

    public void setLikedposts(List<Post> likedposts) {
        this.likedposts = likedposts;
    }

    public List<Post> getDislikedposts() {
        return dislikedposts;
    }

    public void setDislikedposts(List<Post> dislikedposts) {
        this.dislikedposts = dislikedposts;
    }

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
        for(int i=0; i<this.reviews.size(); i++){
            if(this.reviews.get(i).getId()==review.getId()){
                this.reviews.remove(i);
                break;
            }
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
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

    public void addLikedPost(Post post){
        this.likedposts.add(post);
    }

    public void deleteLikedPost(Post post){
        for(int i=0; i<this.likedposts.size(); i++){
            if(this.likedposts.get(i).getId()==post.getId()){
                this.likedposts.remove(i);
                break;
            }
        }
    }

    public void addDislikedPost(Post post){
        this.dislikedposts.add(post);
    }

    public void deleteDislikedPost(Post post){
        for(int i=0; i<this.dislikedposts.size(); i++){
            if(this.dislikedposts.get(i).getId()==post.getId()){
                this.dislikedposts.remove(i);
                break;
            }
        }
    }

    public void addLikedComment(Comment comment){
        this.likedcomments.add(comment);
    }

    public void deleteLikedComment(Comment comment){
        for(int i=0; i<this.likedcomments.size(); i++){
            if(this.likedcomments.get(i).getId()==comment.getId()){
                this.likedcomments.remove(i);
                break;
            }
        }
    }

    public void addDislikedComment(Comment comment){
        this.dislikedcomments.add(comment);
    }

    public void deleteDislikedComment(Comment comment){
        for(int i=0; i<this.dislikedcomments.size(); i++){
            if(this.dislikedcomments.get(i).getId()==comment.getId()){
                this.dislikedcomments.remove(i);
                break;
            }
        }
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
        return getEmail().equals(user.getEmail()) && getFirstname().equals(user.getFirstname()) && getLastname().equals(user.getLastname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getFirstname(), getLastname());
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Review> getLikedreviews() {
        return likedreviews;
    }

    public void setLikedreviews(List<Review> likedreviews) {
        this.likedreviews = likedreviews;
    }

    public List<Review> getDislikedreviews() {
        return dislikedreviews;
    }

    public void setDislikedreviews(List<Review> dislikedreviews) {
        this.dislikedreviews = dislikedreviews;
    }

    public void addPost(Post post){
        this.posts.add(post);
    }
}
