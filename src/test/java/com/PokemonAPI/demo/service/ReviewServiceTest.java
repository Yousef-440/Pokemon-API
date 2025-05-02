package com.PokemonAPI.demo.service;

import com.PokemonAPI.demo.dto.PokemonDto;
import com.PokemonAPI.demo.dto.ReviewDto;
import com.PokemonAPI.demo.model.Pokemon;
import com.PokemonAPI.demo.model.Review;
import com.PokemonAPI.demo.repository.PokemonRepo;
import com.PokemonAPI.demo.repository.ReviewRepository;
import com.PokemonAPI.demo.service.impl.ReviewServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private PokemonRepo pokemonRepo;
    @InjectMocks
    private ReviewServiceImp reviewService;

    private Pokemon pokemon;
    private Review review;
    private ReviewDto reviewDto;

    @BeforeEach
    public void init(){
        pokemon = Pokemon.builder().name("pikachu").type("electric").build();
        review = Review.builder().content("Testing").title("Review Testing").stars(4).build();
        reviewDto = ReviewDto.builder().title("review Title").content("test content").stars(5).build();
    }

    @Test
    public void ReviewService_createReview(){
        when(pokemonRepo.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto savedReview = reviewService.createReview(pokemon.getId() , reviewDto);

        Assertions.assertThat(savedReview).isNotNull();
    }

    @Test
    public void ReviewService_FindById(){
        when(reviewRepository.findByPokemonId(1)).thenReturn(Arrays.asList(review));

        List<ReviewDto> pokemonReturn = reviewService.getReviewsByPokemonId(1);

        Assertions.assertThat(pokemonReturn).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewById(){
        review.setPokemon(pokemon);

        when(pokemonRepo.findById(1)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        ReviewDto reviewReturn = reviewService.getReviewsById(1 , 1);

        Assertions.assertThat(reviewReturn).isNotNull();
    }

    @Test
    public void ReviewService_UpdatePokemon(){

        pokemon.setReviews(Arrays.asList(review));
        review.setPokemon(pokemon);
        when(pokemonRepo.findById(1)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        when(reviewRepository.save(review)).thenReturn(review);

        ReviewDto update = reviewService.UpdateReviewById(1 , 1 , reviewDto);

        Assertions.assertThat(update).isNotNull();
    }

    @Test
    public void ReviewService_Delete(){

        pokemon.setReviews(Arrays.asList(review));
        review.setPokemon(pokemon);

        when(pokemonRepo.findById(1)).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        assertAll(() -> reviewService.DeleteReviewById(1 , 1));
    }
}
