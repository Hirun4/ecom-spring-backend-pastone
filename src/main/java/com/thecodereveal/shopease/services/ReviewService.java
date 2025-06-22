package com.thecodereveal.shopease.services;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



import com.thecodereveal.shopease.dto.OrderRequest;
import com.thecodereveal.shopease.dto.ReviewDTO;
import com.thecodereveal.shopease.dto.ProductOrderCountDto;
import com.thecodereveal.shopease.entities.*;
import com.thecodereveal.shopease.mapper.ProductMapper;
import com.thecodereveal.shopease.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewDTO> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ReviewDTO addReview(ReviewDTO dto) {
        Review review = new Review();
        review.setProductId(dto.getProductId());
        review.setUserId(dto.getUserId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setUserName(dto.getUserName());
        Review saved = reviewRepository.save(review);
        return toDTO(saved);
    }

    public void deleteReview(Long reviewId, UUID userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        if (!review.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        reviewRepository.deleteById(reviewId);
    }

    private ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setProductId(review.getProductId());
        dto.setUserId(review.getUserId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setUserName(review.getUserName());
        dto.setCreatedAt(review.getCreatedAt().toString());
        return dto;
    }
}