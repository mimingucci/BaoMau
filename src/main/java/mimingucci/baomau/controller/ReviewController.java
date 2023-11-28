package mimingucci.baomau.controller;

import mimingucci.baomau.entity.Review;
import mimingucci.baomau.exception.BaoMauNotFoundException;
import mimingucci.baomau.exception.CustomerNotFoundException;
import mimingucci.baomau.repository.ReviewRepository;
import mimingucci.baomau.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
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
    public ResponseEntity<?> createReview(@RequestParam int customerid, @RequestParam int baomauid, @RequestBody Review review){
        try {
            return new ResponseEntity<>(reviewService.createReview(review.getHeadline(), review.getComment(), review.getRating(), customerid, baomauid), HttpStatus.CREATED);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (BaoMauNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> getReview(@PathVariable(name = "id") int id){
        Review review=reviewRepository.findById(id).get();
        if(review==null) {
            return new ResponseEntity<>("Khong tim thay review voi id: "+id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review, HttpStatus.FOUND);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable(name = "id") int id){
        reviewRepository.deleteById(id);
        return new ResponseEntity<>("Da xoa review voi id: "+id, HttpStatus.OK);
    }
}
