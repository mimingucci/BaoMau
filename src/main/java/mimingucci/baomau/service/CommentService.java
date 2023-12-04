package mimingucci.baomau.service;

import mimingucci.baomau.entity.Comment;
import mimingucci.baomau.entity.Post;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.exception.PostNotFoundException;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.CommentRepository;
import mimingucci.baomau.repository.PostRepository;
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

     public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserService userService) {
          this.commentRepository = commentRepository;
          this.postRepository = postRepository;
          this.userService = userService;
     }

     public Comment createComment(int postid, int userid, String content) throws PostNotFoundException, UserNotFoundException {
          Post post=postRepository.findById(postid).get();
          if(post==null){
               throw new PostNotFoundException("Khong tim thay bai post voi id: "+postid);
          }
          User author=userService.findUserById(userid);
          Comment comment=new Comment();
          comment.setCommenttime(new Date());
          comment.setAuthor(author);
          comment.setContent(content);
          comment.setAgree(new ArrayList<>());
          comment.setDisagree(new ArrayList<>());
          Comment cm=commentRepository.save(comment);
          post.getComments().add(cm);
          postRepository.save(post);
          return cm;
     }

     public void deleteComment(int commentid){
          commentRepository.deleteById(commentid);
     }

     public Comment updateComment(int id, String content){
          commentRepository.updateComment(id, content);
          return commentRepository.findById(id).get();
     }

     public void updateAgree(int commentid, int userid) throws UserNotFoundException {
          Comment comment=commentRepository.findById(commentid).get();
          User user=userService.findUserById(userid);
          comment.getAgree().add(user);
          if(comment.getDisagree().contains(user))comment.getDisagree().remove(user);
          for(int i=0; i<comment.getDisagree().size(); i++){
               if(comment.getDisagree().get(i).getId()==user.getId()){
                    comment.getDisagree().remove(i);
               }
          }
          commentRepository.save(comment);
     }

     public void updateDisagree(int commentid, int userid) throws UserNotFoundException {
          Comment comment=commentRepository.findById(commentid).get();
          User user=userService.findUserById(userid);
          comment.getDisagree().add(user);
          for(int i=0; i<comment.getAgree().size(); i++){
               if(comment.getAgree().get(i).getId()==user.getId()){
                    comment.getAgree().remove(i);
               }
          }
          commentRepository.save(comment);
     }
}
