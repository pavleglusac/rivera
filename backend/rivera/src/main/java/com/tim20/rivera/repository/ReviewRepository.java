package com.tim20.rivera.repository;

import com.tim20.rivera.model.Pricelist;
import com.tim20.rivera.model.Review;
import com.tim20.rivera.model.ReviewStatus;
import com.tim20.rivera.service.ReviewService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository  extends JpaRepository<Review, Integer> {

    List<Review> getReviewsByStatus(ReviewStatus reviewStatus);
}
