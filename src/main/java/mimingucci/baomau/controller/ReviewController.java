package mimingucci.baomau.controller;

import mimingucci.baomau.entity.Review;
import mimingucci.baomau.exception.ReviewNotFoundException;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.repository.ReviewRepository;
import mimingucci.baomau.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/review")
@Validated
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createReview(@RequestParam(name = "authornickname") String authornickname, @RequestParam(name = "usernickname") String usernickname, @RequestBody Review review){
        try {
            return new ResponseEntity<>(reviewService.createReview(review.getHeadline(), review.getContent(), review.getRating(), authornickname, usernickname), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/get/all")
    public ResponseEntity<?> getAll(){
        Set<Review> reviews=new HashSet<>(reviewRepository.findAll());
        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> getReview(@PathVariable(name = "id") int id){
        Review review=reviewRepository.findById(id).get();
        if(review==null) {
            return new ResponseEntity<>("Khong tim thay review voi id: "+id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review, HttpStatus.FOUND);
    }

    @GetMapping(path = "/get/user/{nickname}")
    public ResponseEntity<?> getReviewByNickname(@PathVariable(name = "nickname") String nickname){
        List<Review> review=reviewService.getReviewsByUser(nickname);
        if(review==null) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
        return new ResponseEntity<>(review, HttpStatus.FOUND);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable(name = "id") int id){
        reviewRepository.deleteById(id);
        return new ResponseEntity<>("Da xoa review voi id: "+id, HttpStatus.OK);
    }

    @PutMapping(path = "/update/agree/{id}")
    public ResponseEntity<?> updateAgree(@RequestParam(name = "nickname") String nickname, @PathVariable(name = "id") int id) throws UserNotFoundException {
        try {
            reviewService.updateAgree(id, nickname);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Da update danh sach agree cua review", HttpStatus.OK);
    }

    @PutMapping(path = "/update/disagree/{id}")
    public ResponseEntity<?> updateDisagree(@RequestParam(name = "nickname") String nickname, @PathVariable(name = "id") int id) throws UserNotFoundException {
        try {
            reviewService.updateDisagree(id, nickname);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Da update danh sach disagree cua review", HttpStatus.OK);
    }
}
