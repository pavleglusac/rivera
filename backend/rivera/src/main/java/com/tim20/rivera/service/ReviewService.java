package com.tim20.rivera.service;

import com.tim20.rivera.dto.ReviewProfileDTO;
import com.tim20.rivera.model.Review;
import com.tim20.rivera.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ClientService clientService;

    public ReviewProfileDTO reviewToRPDTO(Review review){
        ReviewProfileDTO reviewProfileDTO = new ReviewProfileDTO();
        reviewProfileDTO.setId(review.getId());
        reviewProfileDTO.setClient(clientService.clientToCRDto(review.getClient()));
        reviewProfileDTO.setPosted(review.getPosted());
        reviewProfileDTO.setScore(review.getScore());
        reviewProfileDTO.setText(review.getText());
        return reviewProfileDTO;
    }
}
