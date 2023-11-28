package mimingucci.baomau.service;

import jakarta.transaction.Transactional;
import mimingucci.baomau.entity.Comment;
import mimingucci.baomau.entity.Customer;
import mimingucci.baomau.entity.Post;
import mimingucci.baomau.exception.CustomerNotFoundException;
import mimingucci.baomau.exception.PostNotFoundException;
import mimingucci.baomau.repository.CommentRepository;
import mimingucci.baomau.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     private CustomerService customerService;

     public Comment createComment(int postid, int customerid,String content) throws PostNotFoundException, CustomerNotFoundException {
          Post post=postRepository.findById(postid).get();
          if(post==null){
               throw new PostNotFoundException("Khong tim thay bai post voi id: "+postid);
          }
          Customer customer=customerService.findCustomerById(customerid);
          Comment comment=new Comment();
          comment.setCommenttime(new Date());
          comment.setAuthor(customer);
          comment.setContent(content);
          comment.setAgree(new HashSet<>());
          comment.setDisagree(new HashSet<>());
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

     public void updateAgree(int commentid, int customerid){
          Comment comment=commentRepository.findById(commentid).get();
          comment.getAgree().add(customerid);
          if(comment.getDisagree().contains(customerid))comment.getDisagree().remove(customerid);
          commentRepository.save(comment);
     }

     public void updateDisagree(int commentid, int customerid){
          Comment comment=commentRepository.findById(commentid).get();
          comment.getDisagree().add(customerid);
          if(comment.getAgree().contains(customerid))comment.getAgree().remove(customerid);
          commentRepository.save(comment);
     }
}
