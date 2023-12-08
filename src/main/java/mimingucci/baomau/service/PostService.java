package mimingucci.baomau.service;

import mimingucci.baomau.entity.Comment;
import mimingucci.baomau.entity.Post;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.entity.UserDTO;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.PostRepository;
import mimingucci.baomau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(String nickname, String headline, String content) throws UserNotFoundException {
        User user=userService.findUserByNickname(nickname);
        Post post=new Post();
        post.setAgree(new HashSet<>());
        post.setDisagree(new HashSet<>());
        post.setHeadline(headline);
        post.setContent(content);
        post.setComments(new HashSet<>());
        post.setPostedtime(new Date());
        post.setAuthor(user.getNickname());
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

    public Post updatePost(int postid, String headline, String content){
        Post post=postRepository.findById(postid).get();
        if(headline==null || headline.length()==0){
            headline=post.getHeadline();
        }else{
            post.setHeadline(headline);
        }
        if(content==null || content.length()==0){
            content=post.getContent();
        }else{
            post.setContent(content);
        }
        postRepository.updatePost(postid, headline, content);
        return post;
    }

    public void updateAgree(int postid, String nickname){
        Post post=postRepository.findById(postid).get();
        User user=userRepository.findByNickname(nickname);
        UserDTO dto=new UserDTO(user.getId(), user.getNickname());
        post.getAgree().add(dto);
        if(post.getDisagree().contains(dto)){
            post.getDisagree().remove(dto);
        }
        Post p=postRepository.save(post);
        user.addLikedPost(p);
        userRepository.save(user);
    }

    public void updateDisagree(int postid, String nickname){
        Post post=postRepository.findById(postid).get();
        User user=userRepository.findByNickname(nickname);
        UserDTO dto=new UserDTO(user.getId(), user.getNickname());
        post.getDisagree().add(dto);
        if(post.getAgree().contains(dto)){
            post.getAgree().remove(dto);
        }
        Post p=postRepository.save(post);
        user.addDislikedPost(p);
        userRepository.save(user);
    }
}
