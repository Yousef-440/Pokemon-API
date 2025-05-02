package com.PokemonAPI.demo.service;

import com.PokemonAPI.demo.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto createReview(int pokemonId , ReviewDto reviewDto);

    List<ReviewDto> getReviewsByPokemonId(int id);

    ReviewDto getReviewsById(int reviewId , int pokemonId);

    ReviewDto UpdateReviewById(int pokemonId , int reviewId ,ReviewDto reviewDto);

    void DeleteReviewById(int PokemonId , int reviewId);
}
