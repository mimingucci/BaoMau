package mimingucci.baomau.service;

import jakarta.transaction.Transactional;
import mimingucci.baomau.entity.Customer;
import mimingucci.baomau.entity.Post;
import mimingucci.baomau.exception.CustomerNotFoundException;
import mimingucci.baomau.repository.CustomerRepository;
import mimingucci.baomau.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    public Post createPost(int customerid, String headline, String comment) throws CustomerNotFoundException {
        Customer customer=customerService.findCustomerById(customerid);
        Post post=new Post();
        post.setAgree(new HashSet<>());
        post.setDisagree(new HashSet<>());
        post.setHeadline(headline);
        post.setComment(comment);
        post.setComments(new ArrayList<>());
        post.setPostedtime(new Date());
        Post savedPost=postRepository.save(post);
        customer.addPost(savedPost);
        customerRepository.save(customer);
        return savedPost;
    }

    public void deletePost(int postid){
        postRepository.deleteById(postid);
    }

    public PostService(PostRepository postRepository, CustomerService customerService, CustomerRepository customerRepository) {
        this.postRepository = postRepository;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
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

    public void updateAgree(int postid, int customerid){
        Post post=postRepository.findById(postid).get();
        post.getAgree().add(customerid);
        if(post.getDisagree().contains(customerid))post.getDisagree().remove(customerid);
        postRepository.save(post);
    }

    public void updateDisagree(int postid, int customerid){
        Post post=postRepository.findById(postid).get();
        post.getDisagree().add(customerid);
        if(post.getAgree().contains(customerid))post.getAgree().remove(customerid);
        postRepository.save(post);
    }
}
