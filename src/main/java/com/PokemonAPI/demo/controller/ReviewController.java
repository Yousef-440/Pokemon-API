package com.PokemonAPI.demo.controller;

import com.PokemonAPI.demo.dto.ReviewDto;
import com.PokemonAPI.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/pokemon/{pokemonId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable("pokemonId") int pokemonId , @RequestBody ReviewDto reviewDto){
        return new ResponseEntity<>(reviewService.createReview(pokemonId , reviewDto) , HttpStatus.CREATED);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews")
    public List<ReviewDto> getReviewByPokemonId(@PathVariable("pokemonId") int pokemonId){
        return reviewService.getReviewsByPokemonId(pokemonId);
    }

    @GetMapping("/pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "pokemonId") int pokemonId , @PathVariable("id") int id){
        ReviewDto reviewDto = reviewService.getReviewsById(pokemonId , id);
        return new ResponseEntity<>(reviewDto , HttpStatus.OK);
    }

    @PutMapping("/pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> UpdateReviewById(@PathVariable(value = "pokemonId") int id , @PathVariable(value = "reviewId")
                                                   int reviewId , @RequestBody ReviewDto reviewDto){
        ReviewDto reviewDto1 = reviewService.UpdateReviewById(id , reviewId, reviewDto);
        return ResponseEntity.ok(reviewDto1);
    }

    @DeleteMapping("/pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<String> DeleteReviewById(@PathVariable int pokemonId , @PathVariable int reviewId){
        reviewService.DeleteReviewById(pokemonId , reviewId);
        String str = "Review Was Deleted Successfully";
        return ResponseEntity.ok(str);
    }

}
