package mimingucci.baomau.service;

import mimingucci.baomau.entity.Comment;
import mimingucci.baomau.entity.Post;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.entity.UserDTO;
import mimingucci.baomau.exception.CommentNotFoundException;
import mimingucci.baomau.exception.PostNotFoundException;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.CommentRepository;
import mimingucci.baomau.repository.PostRepository;
import mimingucci.baomau.repository.UserDTORepository;
import mimingucci.baomau.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@Service
@Transactional
public class CommentService {
     @Autowired
     private CommentRepository commentRepository;

     @Autowired
     private PostRepository postRepository;

     @Autowired
     private UserService userService;

     @Autowired
     private UserDTORepository userDTORepository;

     @Autowired
     private UserRepository userRepository;

     public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserService userService, UserDTORepository userDTORepository, UserRepository userRepository) {
          this.commentRepository = commentRepository;
          this.postRepository = postRepository;
          this.userService = userService;
          this.userDTORepository = userDTORepository;
          this.userRepository = userRepository;
     }

     public Comment createComment(int postid, String nickname, String content) throws PostNotFoundException, UserNotFoundException {
          Post post=postRepository.findById(postid).get();
          if(post==null){
               throw new PostNotFoundException("Khong tim thay bai post voi id: "+postid);
          }
          User author=userService.findUserByNickname(nickname);
          Comment comment=new Comment();
          comment.setCommenttime(new Date());
          comment.setAuthor(author.getNickname());
          comment.setContent(content);
          comment.setPost(post.getId());
          comment.setAgree(new HashSet<>());
          comment.setDisagree(new HashSet<>());
          Comment cm=commentRepository.save(comment);
          post.getComments().add(cm);
          postRepository.save(post);
          author.getComments().add(cm);
          userRepository.save(author);
          return cm;
     }

     public void deleteComment(int id) throws CommentNotFoundException {
          Comment comment=commentRepository.findById(id).get();
          if(comment==null){
               throw new CommentNotFoundException("Khong tim thay comment voi id: "+id);
          }
          for(UserDTO i : comment.getAgree()){
               User user=userRepository.findByNickname(i.getNickname());
               user.getLikedcomments().remove(comment);
               userRepository.save(user);
          }
          for(UserDTO i : comment.getDisagree()){
               User user=userRepository.findByNickname(i.getNickname());
               user.getDislikedcomments().remove(comment);
               userRepository.save(user);
          }
          comment.getAgree().clear();
          comment.getDisagree().clear();
          User author=userRepository.findByNickname(comment.getAuthor());
          author.getComments().remove(comment);
          userRepository.save(author);
          commentRepository.deleteById(id);
     }

     public Comment updateComment(int id, String content){
          commentRepository.updateComment(id, content);
          return commentRepository.findById(id).get();
     }

     public void updateAgree(int commentid, String nickname) throws UserNotFoundException {
          Comment comment=commentRepository.findById(commentid).get();
          User user=userService.findUserByNickname(nickname);
          UserDTO dto=new UserDTO(user.getId(), user.getNickname());
          comment.addAgree(dto);
          if(comment.getDisagree().contains(dto)){
               comment.getDisagree().remove(dto);
          }
          Comment cm=commentRepository.save(comment);
          user.addLikedComment(cm);
          userRepository.save(user);
     }

     public void updateDisagree(int commentid, String nickname) throws UserNotFoundException {
          Comment comment=commentRepository.findById(commentid).get();
          User user=userService.findUserByNickname(nickname);
          UserDTO dto=new UserDTO(user.getId(), user.getNickname());
          comment.addDisagree(dto);
          if(comment.getAgree().contains(dto)){
               comment.getAgree().remove(dto);
          }
          Comment cm=commentRepository.save(comment);
          user.addDislikedComment(cm);
          userRepository.save(user);
     }

     public void deleteLike(int id, String nickname) throws CommentNotFoundException, UserNotFoundException {
          Comment comment=commentRepository.findById(id).get();
          if(comment==null){
               throw new CommentNotFoundException("Khong tim thay comment void id: "+id);
          }
          UserDTO dto=userDTORepository.findByNickname(nickname);
          if(dto==null){
               throw new UserNotFoundException("Khong tim thay nguoi dung voi nickname: "+nickname);
          }
          if(comment.getAgree().contains(dto)){
               comment.getAgree().remove(dto);
          }
     }

     public void deleteDislike(int id, String nickname) throws CommentNotFoundException, UserNotFoundException {
          Comment comment=commentRepository.findById(id).get();
          if(comment==null){
               throw new CommentNotFoundException("Khong tim thay comment void id: "+id);
          }
          UserDTO dto=userDTORepository.findByNickname(nickname);
          if(dto==null){
               throw new UserNotFoundException("Khong tim thay nguoi dung voi nickname: "+nickname);
          }
          if(comment.getDisagree().contains(dto)){
               comment.getDisagree().remove(dto);
          }
     }
}
