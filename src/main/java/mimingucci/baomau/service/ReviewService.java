package mimingucci.baomau.service;

import mimingucci.baomau.entity.User;
import mimingucci.baomau.entity.Review;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.exception.ReviewNotFoundException;
import mimingucci.baomau.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@Service
@Transactional
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    public ReviewService(ReviewRepository reviewRepository, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }

    public Review createReview(String headline, String comment, int rating, int authorid, int id) throws UserNotFoundException {
        User author=userService.findUserById(authorid);
        User user = userService.findById(id);
        Review review=new Review();
        review.setHeadline(headline);
        review.setComment(comment);
        review.setAuthor(author);
        review.setRating(rating);
        review.setReviewtime(new Date());
        review.setDisagree(new ArrayList<>());
        review.setDisagree(new ArrayList<>());
        Review rv=reviewRepository.save(review);
        user.getReviews().add(rv);
        userService.updateUser(id, user);
        return rv;
    }

    public void deleteReview(int reviewid){
        reviewRepository.deleteById(reviewid);
    }

    public void updateReview(int reviewid, String headline, String comment) throws ReviewNotFoundException {
        Review review=reviewRepository.findById(reviewid).get();
        if(review==null){
            throw new ReviewNotFoundException("Khong tim thay review voi id: "+reviewid);
        }
        if(headline==null || headline.length()==0){
            headline=review.getHeadline();
        }
        if(comment==null || comment.length()==0){
            comment=review.getComment();
        }
        reviewRepository.updateReview(reviewid, headline, comment);
    }
}
