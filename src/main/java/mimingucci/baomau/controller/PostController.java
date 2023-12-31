package mimingucci.baomau.controller;

import mimingucci.baomau.entity.Post;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.entity.UserDTO;
import mimingucci.baomau.exception.DislikeBeforeException;
import mimingucci.baomau.exception.LikeBeforeException;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.PostRepository;
import mimingucci.baomau.repository.UserRepository;
import mimingucci.baomau.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
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
    public ResponseEntity<?> createPost(@RequestParam(name = "nickname") String nickname, @RequestBody Post post){
        try {
            return new ResponseEntity<>(postService.createPost(nickname, post.getHeadline(), post.getContent()), HttpStatus.CREATED);
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

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(){
        Set<Post> posts=new HashSet<>(postRepository.findAll());
        List<Post> postList=new ArrayList<>(posts);
        Collections.sort(postList, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return -o1.getPostedtime().compareTo(o2.getPostedtime());
            }
        });
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable(name = "id") int id, @RequestBody Post post){
         return new ResponseEntity<>(postService.updatePost(id, post.getHeadline(), post.getContent()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "id") int id){
        postService.deletePost(id);
        return ResponseEntity.ok("Da xoa bai post voi id: "+id);
    }

    @PutMapping(path = "/update/agree/{id}")
    public ResponseEntity<?> updateAgree(@RequestParam(name = "nickname") String nickname, @PathVariable(name = "id") int id){
        try {
            postService.updateAgree(id, nickname);
            return ResponseEntity.ok("Da them vao danh sach yeu thich bai post");
        } catch (LikeBeforeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/update/disagree/{id}")
    public ResponseEntity<?> updateDisagree(@RequestParam(name = "nickname") String nickname, @PathVariable(name = "id") int id){
        try {
            postService.updateDisagree(id, nickname);
            return ResponseEntity.ok("Da them vao danh sach khong yeu thich bai post");
        } catch (DislikeBeforeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/get/allagree/{id}")
    public ResponseEntity<?> getAllAgree(@PathVariable(name = "id") int id){
        Set<UserDTO> users=postRepository.findById(id).get().getAgree();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/get/alldisagree/{id}")
    public ResponseEntity<?> getAllDisagree(@PathVariable(name = "id") int id){
        Set<UserDTO> users=postRepository.findById(id).get().getDisagree();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
