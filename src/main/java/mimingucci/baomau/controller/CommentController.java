package mimingucci.baomau.controller;

import mimingucci.baomau.entity.Comment;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.entity.UserDTO;
import mimingucci.baomau.exception.CommentNotFoundException;
import mimingucci.baomau.exception.PostNotFoundException;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.CommentRepository;
import mimingucci.baomau.repository.UserRepository;
import mimingucci.baomau.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/comment")
@Validated
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public CommentController(CommentService commentService, CommentRepository commentRepository, UserRepository userRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createComment(@RequestParam(name = "postid") int postid, @RequestParam(name = "nickname") String nickname, @RequestBody Comment comment){
        try {
            return new ResponseEntity<>(commentService.createComment(postid, nickname, comment.getContent()), HttpStatus.CREATED);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> getComment(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(commentRepository.findById(id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "id") int id){
        try {
            commentService.deleteComment(id);
        } catch (CommentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Da xoa comment voi id: "+id, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable(name = "id") int id, @RequestBody Comment comment){
        return new ResponseEntity<>(commentService.updateComment(id, comment.getContent()), HttpStatus.OK);
    }

    @PutMapping(path = "/update/agree/{id}")
    public ResponseEntity<?> updateAgree(@RequestParam(name = "nickname") String nickname, @PathVariable(name = "id") int id) throws UserNotFoundException {
        commentService.updateAgree(id, nickname);
        return new ResponseEntity<>("Da update danh sach agree cua commennt", HttpStatus.OK);
    }

    @PutMapping(path = "/update/disagree/{id}")
    public ResponseEntity<?> updateDisagree(@RequestParam(name = "nickname") String nickname, @PathVariable(name = "id") int id) throws UserNotFoundException {
        commentService.updateDisagree(id, nickname);
        return new ResponseEntity<>("Da update danh sach disagree cua commennt", HttpStatus.OK);
    }

    @GetMapping(path = "/get/allagree/{id}")
    public ResponseEntity<?> getAllAgree(@PathVariable(name = "id") int id){
        Set<UserDTO> users=commentRepository.findById(id).get().getAgree();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/get/alldisagree/{id}")
    public ResponseEntity<?> getAllDisagree(@PathVariable(name = "id") int id){
        Set<UserDTO> users=commentRepository.findById(id).get().getDisagree();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping(path = "/update/like")
    public ResponseEntity<?> updateLikedComment(@RequestParam(name = "nickname") String nickname, @RequestParam(name = "id") int id){
        try {
            commentService.updateAgree(id, nickname);
            return new ResponseEntity<>("Da them vao danh sach like cua comment", HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/update/dislike")
    public ResponseEntity<?> updateDislikedComment(@RequestParam(name = "nickname") String nickname, @RequestParam(name = "id") int id){
        try {
            commentService.updateDisagree(id, nickname);
            return new ResponseEntity<>("Da them vao danh sach dislike cua comment", HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/update/deletelike")
    public ResponseEntity<?> deleteLike(@RequestParam(name = "nickname") String nickname, @RequestParam(name = "id") int id){
        try {
            commentService.deleteLike(id, nickname);
            return new ResponseEntity<>("Da xoa nguoi dung void nickname: "+nickname+" khoi danh sach like", HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/update/deletedislike")
    public ResponseEntity<?> deleteDislike(@RequestParam(name = "nickname") String nickname, @RequestParam(name = "id") int id){
        try {
            commentService.deleteDislike(id, nickname);
            return new ResponseEntity<>("Da xoa nguoi dung void nickname: "+nickname+" khoi danh sach dislike", HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
