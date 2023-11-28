package mimingucci.baomau.service;

import jakarta.transaction.Transactional;
import mimingucci.baomau.entity.BaoMau;
import mimingucci.baomau.entity.Customer;
import mimingucci.baomau.entity.Review;
import mimingucci.baomau.exception.BaoMauNotFoundException;
import mimingucci.baomau.exception.CustomerNotFoundException;
import mimingucci.baomau.exception.ReviewNotFoundException;
import mimingucci.baomau.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;

@Service
@Transactional
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BaoMauService baoMauService;

    public ReviewService(ReviewRepository reviewRepository, CustomerService customerService, BaoMauService baoMauService) {
        this.reviewRepository = reviewRepository;
        this.customerService = customerService;
        this.baoMauService = baoMauService;
    }

    public Review createReview(String headline, String comment, int rating, int customerid, int baomauid) throws CustomerNotFoundException, BaoMauNotFoundException {
        Customer customer=customerService.findCustomerById(customerid);
        BaoMau baoMau=baoMauService.findById(baomauid);
        Review review=new Review();
        review.setHeadline(headline);
        review.setComment(comment);
        review.setCustomer(customer);
        review.setRating(rating);
        review.setReviewtime(new Date());
        review.setDisagree(new HashSet<>());
        review.setDisagree(new HashSet<>());
        Review rv=reviewRepository.save(review);
        baoMau.getReviews().add(rv);
        baoMauService.updateBaoMau(baomauid, baoMau);
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
