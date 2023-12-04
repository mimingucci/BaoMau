package mimingucci.baomau.service;

import mimingucci.baomau.entity.Post;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.PostRepository;
import mimingucci.baomau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(int userid, String headline, String comment) throws UserNotFoundException {
        User user=userService.findUserById(userid);
        Post post=new Post();
        post.setAgree(new ArrayList<>());
        post.setDisagree(new ArrayList<>());
        post.setHeadline(headline);
        post.setComment(comment);
        post.setComments(new ArrayList<>());
        post.setPostedtime(new Date());
        Post savedPost=postRepository.save(post);
        user.addPost(savedPost);
        userRepository.save(user);
        return savedPost;
    }

    public void deletePost(int postid){
        postRepository.deleteById(postid);
    }

    public PostService(PostRepository postRepository, UserService userService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public Post updatePost(int postid, String headline, String comment){
        Post post=postRepository.findById(postid).get();
        if(headline==null || headline.length()==0){
            headline=post.getHeadline();
        }
        if(comment==null || comment.length()==0){
            comment=post.getComment();
        }
        postRepository.updatePost(postid, headline, comment);
        return postRepository.findById(postid).get();
    }

    public void updateAgree(int postid, int userid){
        Post post=postRepository.findById(postid).get();
        User user=userRepository.findById(userid).get();
        post.getAgree().add(user);
        for(int i=0; i<post.getDisagree().size(); i++){
            if(post.getDisagree().get(i).getId()==user.getId()){
                post.getDisagree().remove(i);
            }
        }
        postRepository.save(post);
    }

    public void updateDisagree(int postid, int userid){
        Post post=postRepository.findById(postid).get();
        User user=userRepository.findById(userid).get();
        post.getDisagree().add(user);
        for(int i=0; i<post.getAgree().size(); i++){
            if(post.getAgree().get(i).getId()==user.getId()){
                post.getAgree().remove(i);
            }
        }
        postRepository.save(post);
    }
}
