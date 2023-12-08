package mimingucci.baomau.service;

import mimingucci.baomau.entity.Comment;
import mimingucci.baomau.entity.User;
import mimingucci.baomau.entity.Review;
import mimingucci.baomau.entity.UserDTO;
import mimingucci.baomau.exception.ReviewExistBeforeException;
import mimingucci.baomau.exception.UserNotFoundException;
import mimingucci.baomau.exception.ReviewNotFoundException;
import mimingucci.baomau.repository.ReviewRepository;
import mimingucci.baomau.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, UserService userService, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public boolean reviewExistsBefore(String author, String user){
        Review review=reviewRepository.findByAuthorAndUser(author, user);
        if(review==null){
            return false;
        }
        return true;
    }

    public Review createReview(String headline, String content, int rating, String authornickname, String x) throws UserNotFoundException, ReviewExistBeforeException {
        User author=userService.findUserByNickname(authornickname);
        User user = userService.findUserByNickname(x);
        if(author==null || user==null){
            throw new UserNotFoundException("Khong tim thay user");
        }
        if(reviewExistsBefore(authornickname, x)){
            throw new ReviewExistBeforeException("Da ton tai review voi author: "+authornickname+" va user: "+x);
        }
        Review review=new Review();
        review.setHeadline(headline);
        review.setContent(content);
        review.setAuthor(author.getNickname());
        review.setUser(x);
        review.setRating(rating);
        review.setReviewtime(new Date());
        review.setDisagree(new HashSet<>());
        review.setDisagree(new HashSet<>());
        Review rv=reviewRepository.save(review);
        user.getReviews().add(rv);
        double calculatedRating=0d;
        for(Review i : user.getReviews()){
            calculatedRating+=i.getRating();
        }
        calculatedRating/=user.getReviews().size();
        calculatedRating=Math.round(calculatedRating*100)/100.0;
        user.setRating(calculatedRating);
        userService.updateUser(x, user);
        return rv;
    }

    public void deleteReview(int reviewid){
        reviewRepository.deleteById(reviewid);
    }

    public void updateReview(int reviewid, String headline, String content) throws ReviewNotFoundException {
        Review review=reviewRepository.findById(reviewid).get();
        if(review==null){
            throw new ReviewNotFoundException("Khong tim thay review voi id: "+reviewid);
        }
        if(headline==null || headline.length()==0){
            headline=review.getHeadline();
        }
        if(content==null || content.length()==0){
            content=review.getContent();
        }
        reviewRepository.updateReview(reviewid, headline, content);
    }

    public void updateAgree(int id, String nickname) throws UserNotFoundException, ReviewNotFoundException {
        Review review=reviewRepository.findById(id).get();
        if(review==null){
            throw new ReviewNotFoundException("Khong tim thay bai review voi id: "+id);
        }
        User user=userService.findUserByNickname(nickname);
        if(user==null){
            throw new UserNotFoundException("Khong tim thay user voi id: "+id);
        }
        UserDTO dto=new UserDTO(user.getId(), user.getNickname());
        review.addAgree(dto);
        Review rv=reviewRepository.save(review);
        user.addLikedReview(rv);
        userRepository.save(user);
    }

    public void updateDisagree(int id, String nickname) throws UserNotFoundException, ReviewNotFoundException {
        Review review=reviewRepository.findById(id).get();
        if(review==null){
            throw new ReviewNotFoundException("Khong tim thay bai review voi id: "+id);
        }
        User user=userService.findUserByNickname(nickname);
        if(user==null){
            throw new UserNotFoundException("Khong tim thay user voi id: "+id);
        }
        UserDTO dto=new UserDTO(user.getId(), user.getNickname());
        review.addDisagree(dto);
        Review rv=reviewRepository.save(review);
        user.addDislikedReview(rv);
        userRepository.save(user);
    }
}
