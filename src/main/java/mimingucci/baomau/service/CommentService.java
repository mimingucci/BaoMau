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

     public void createComment(int postid, int customerid,String content) throws PostNotFoundException, CustomerNotFoundException {
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
          post.getComments().add(comment);
          postRepository.save(post);
     }

     public void deleteComment(int commentid){
          commentRepository.deleteById(commentid);
     }

     public void updateComment(int id, String content){
          commentRepository.updateComment(id, content);
     }

}
