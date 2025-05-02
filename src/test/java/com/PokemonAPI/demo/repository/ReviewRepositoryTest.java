package com.PokemonAPI.demo.repository;

import com.PokemonAPI.demo.model.Pokemon;
import com.PokemonAPI.demo.model.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PokemonRepo pokemonRepo;

    @Test
    public void ReviewRepo_SaveAll_ReturnSaveReview(){
        //Arrange
        Review review = Review.builder()
                .content("Testing")
                .title("Review Testing")
                .stars(4)
                .build();
        //Act
        Review reviewSaved = reviewRepository.save(review);
        //Assert
        Assertions.assertThat(reviewSaved).isNotNull();
        Assertions.assertThat(reviewSaved.getId()).isGreaterThan(0);
    }

    @Test
    public void GetAll_Review(){
        //Arrange
        Review review1 = Review.builder()
                .content("Test1")
                .title("Review Testing for review1")
                .stars(3).build();
        Review review2 = Review.builder()
                .content("Test2")
                .title("Review Testing for review2")
                .stars(5).build();
        Review review3 = Review.builder()
                .content("Test3")
                .title("Review Testing for review3")
                .stars(1).build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);

        List<Review> listReview = reviewRepository.findAll();

        Assertions.assertThat(listReview).isNotNull();
        Assertions.assertThat(listReview.size()).isEqualTo(3);
    }

    @Test
    public void ReviewRepository_FindById(){
        Review review = Review.builder()
                .content("Testing")
                .title("Test fo Review")
                .stars(2).build();
        reviewRepository.save(review);

        Review reviewId = reviewRepository.findById(review.getId()).get();

        Assertions.assertThat(reviewId).isNotNull();
    }

    @Test
    public void ReviewRepository_FindByPokemonId() {
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("Electric")
                .build();
        pokemonRepo.save(pokemon);

        Review review = Review.builder()
                .content("Testing")
                .title("Test for Review")
                .stars(2)
                .pokemon(pokemon)
                .build();

        reviewRepository.save(review);

        List<Review> findPokemonById = reviewRepository.findByPokemonId(pokemon.getId());

        Assertions.assertThat(findPokemonById).isNotNull();

    }

    @Test
    public void ReviewRepository_UpdatedReview(){
        Review review = Review.builder()
                .content("Testing")
                .title("Test fo Review")
                .stars(2).build();
        reviewRepository.save(review);
        Review saveReview = reviewRepository.findById(review.getId()).get();
        saveReview.setContent("TestingUpdated");
        saveReview.setTitle("Update");
        Review updatedReview = reviewRepository.save(saveReview);
        Assertions.assertThat(updatedReview.getContent()).isNotNull();
        Assertions.assertThat(updatedReview.getTitle()).isNotNull();
    }

    @Test
    public void ReviewRepo_Delete(){
        Review review = Review.builder()
                .content("Testing")
                .title("Test fo Review")
                .stars(2).build();

        reviewRepository.save(review);
        reviewRepository.deleteById(review.getId());

        Optional<Review> reviewID = reviewRepository.findById(review.getId());

        Assertions.assertThat(reviewID).isEmpty();

    }
}
