package com.PokemonAPI.demo.service.impl;

import com.PokemonAPI.demo.dto.ReviewDto;
import com.PokemonAPI.demo.exceptions.PokemonNotFoundException;
import com.PokemonAPI.demo.exceptions.ReviewNotFoundException;
import com.PokemonAPI.demo.model.Pokemon;
import com.PokemonAPI.demo.model.Review;
import com.PokemonAPI.demo.repository.PokemonRepo;
import com.PokemonAPI.demo.repository.ReviewRepository;
import com.PokemonAPI.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImp implements ReviewService {

    @Autowired
    private PokemonRepo pokemonRepo;
    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);

        Pokemon pokemon = pokemonRepo.findById(pokemonId).orElseThrow(()->new PokemonNotFoundException("Pokemon Id not found"));

        review.setPokemon(pokemon);

        reviewRepository.save(review);

        return mapToDto(review);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int id) {

        List<Review> reviews = reviewRepository.findByPokemonId(id);

        return reviews.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

    }

    @Override
    public ReviewDto getReviewsById(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepo.findById(pokemonId).orElseThrow(()->new PokemonNotFoundException("Sorry, the Pokemon ID not found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new ReviewNotFoundException("Sorry,Review Id Not found"));

        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("This review does not blond to a pokemon");
        }
        return mapToDto(review);
    }

    @Override
    public ReviewDto UpdateReviewById(int pokemonId, int reviewId , ReviewDto reviewDto) {
        Pokemon pokemon = pokemonRepo.findById(pokemonId).orElseThrow(()->new PokemonNotFoundException("Sorry, Pokemon Not Found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new ReviewNotFoundException("Sorry, Review By Id Not Found"));

        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("This review does not blond to a pokemon");
        }
        review.setContent(reviewDto.getContent());
        review.setTitle(reviewDto.getTitle());
        review.setStars(reviewDto.getStars());

        Review Updated = reviewRepository.save(review);

        return mapToDto(Updated);
    }

    @Override
    public void DeleteReviewById(int PokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepo.findById(PokemonId).orElseThrow(()->new PokemonNotFoundException("Sorry,Pokemon for {" + PokemonId +"} Id Not Found"));
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new ReviewNotFoundException("Sorry,Review for {"+reviewId+"} Id Not Found"));

        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("This review does not blond to a pokemon");
        }
        reviewRepository.deleteById(reviewId);
    }

    private ReviewDto mapToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());

        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto){
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        return review;
    }
}
