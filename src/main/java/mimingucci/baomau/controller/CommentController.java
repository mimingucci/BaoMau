package mimingucci.baomau.controller;

import mimingucci.baomau.entity.Comment;
import mimingucci.baomau.entity.Customer;
import mimingucci.baomau.exception.CustomerNotFoundException;
import mimingucci.baomau.exception.PostNotFoundException;
import mimingucci.baomau.repository.CommentRepository;
import mimingucci.baomau.repository.CustomerRepository;
import mimingucci.baomau.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/comment")
@Validated
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createComment(@RequestParam(name = "postid") int postid, @RequestParam(name = "customerid") int customerid, @RequestBody Comment comment){
        try {
            return new ResponseEntity<>(commentService.createComment(postid, customerid, comment.getContent()), HttpStatus.CREATED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> getComment(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(commentRepository.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "id") int id){
        commentRepository.deleteById(id);
        return new ResponseEntity<>("Da xoa comment voi id: "+id, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "id") int id, @RequestBody Comment comment){
        return new ResponseEntity<>(commentService.updateComment(id, comment.getContent()), HttpStatus.OK);
    }

    @PutMapping(path = "/update/agree/{id}")
    public ResponseEntity<?> updateAgree(@RequestParam(name = "custommerid") int custommerid, @PathVariable(name = "id") int id){
        commentService.updateAgree(id, custommerid);
        return new ResponseEntity<>("Da update danh sach agree cua commennt", HttpStatus.OK);
    }

    @PutMapping(path = "/update/disagree/{id}")
    public ResponseEntity<?> updateDisagree(@RequestParam(name = "custommerid") int custommerid, @PathVariable(name = "id") int id){
        commentService.updateDisagree(id, custommerid);
        return new ResponseEntity<>("Da update danh sach disagree cua commennt", HttpStatus.OK);
    }

    @GetMapping(path = "/get/allagree/{id}")
    public ResponseEntity<?> getAllAgree(@PathVariable(name = "id") int id){
        Set<Integer> agree=commentRepository.findById(id).get().getAgree();
        List<Customer> customers=new ArrayList<>();
        for(int i : agree){
            Customer c=customerRepository.findById(i).get();
            if(c!=null)customers.add(c);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping(path = "/get/alldisagree/{id}")
    public ResponseEntity<?> getAllDisagree(@PathVariable(name = "id") int id){
        Set<Integer> disagree=commentRepository.findById(id).get().getDisagree();
        List<Customer> customers=new ArrayList<>();
        for(int i : disagree){
            Customer c=customerRepository.findById(i).get();
            if(c!=null)customers.add(c);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}