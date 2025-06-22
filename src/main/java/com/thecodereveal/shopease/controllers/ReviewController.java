package com.thecodereveal.shopease.controllers;

import com.thecodereveal.shopease.dto.ReviewDTO;
import com.thecodereveal.shopease.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{productId}")
    public List<ReviewDTO> getReviews(@PathVariable Long productId) {
        return reviewService.getReviewsByProduct(productId);
    }

    @PostMapping
    public ReviewDTO addReview(@RequestBody ReviewDTO dto) {
        return reviewService.addReview(dto);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId, @RequestParam UUID userId) {
        reviewService.deleteReview(reviewId, userId);
    }
}