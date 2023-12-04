package mimingucci.baomau.controller;

import mimingucci.baomau.entity.Post;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.PostRepository;
import mimingucci.baomau.repository.UserRepository;
import mimingucci.baomau.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/post")
@Validated
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public PostController(PostService postService, PostRepository postRepository, UserRepository userRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createPost(@RequestParam(name = "userid") int userid, @RequestBody Post post){
        try {
            return new ResponseEntity<>(postService.createPost(userid, post.getHeadline(), post.getComment()), HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> getPost(@PathVariable(name = "id") int id){
        Post post=postRepository.findById(id).get();
        if(post==null){
            return new ResponseEntity<>("Khong tim thay bai post voi id: "+id, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable(name = "id") int id, @RequestBody Post post){
         return new ResponseEntity<>(postService.updatePost(id, post.getHeadline(), post.getComment()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id") int id){
        postService.deletePost(id);
        return ResponseEntity.ok("Da xoa bai post voi id: "+id);
    }

    @PutMapping(path = "/update/agree/{id}")
    public ResponseEntity<?> updateAgree(@RequestParam(name = "userid") int userid, @PathVariable(name = "id") int id){
        postService.updateAgree(id, userid);
        return ResponseEntity.ok("Da them vao danh sach yeu thich bai post");
    }

    @PutMapping(path = "/update/disagree/{id}")
    public ResponseEntity<?> updateDisagree(@RequestParam(name = "userid") int userid, @PathVariable(name = "id") int id){
        postService.updateDisagree(id, userid);
        return ResponseEntity.ok("Da them vao danh sach khong yeu thich bai post");
    }

    @GetMapping(path = "/get/allagree/{id}")
    public ResponseEntity<?> getAllAgree(@PathVariable(name = "id") int id){
        List<User> users=postRepository.findById(id).get().getAgree();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/get/alldisagree/{id}")
    public ResponseEntity<?> getAllDisagree(@PathVariable(name = "id") int id){
        List<User> users=postRepository.findById(id).get().getDisagree();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
