package com.PokemonAPI.demo.repository;

import com.PokemonAPI.demo.model.Pokemon;
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
public class PokemonRepositoryTests {
    @Autowired
    private PokemonRepo pokemonRepo;

    @Test
    public void PokemonRepository_SaveAll_ReturnSavedPokemon() {
        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("elect")
                .build();

        //Act
        Pokemon savedPokemon = pokemonRepo.save(pokemon);

        //Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    public void PokemonRepository_GetAll_ReturnMoreThenOnePokemon(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("elect")
                .build();
        Pokemon pokemon2 = Pokemon.builder()
                .name("pikachu")
                .type("elect")
                .build();

        pokemonRepo.save(pokemon);
        pokemonRepo.save(pokemon2);

        List<Pokemon> pokemonList = pokemonRepo.findAll();

        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    public void PokemonRepository_FindById_ReturnPokemon(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("elect")
                .build();

        pokemonRepo.save(pokemon);

        Pokemon pokemonID = pokemonRepo.findById(pokemon.getId()).get();

        Assertions.assertThat(pokemonID).isNotNull();
    }

    @Test
    public void PokemonRepository_FindByType_ReturnPokemon(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("elect")
                .build();

        pokemonRepo.save(pokemon);

        Pokemon pokemonID = pokemonRepo.findByType(pokemon.getType()).get();

        Assertions.assertThat(pokemonID).isNotNull();
    }

    @Test
    public void PokemonRepository_UpdatePokemon_ReturnPokemon(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("elect")
                .build();

        pokemonRepo.save(pokemon);

        Pokemon pokemonID = pokemonRepo.findByType(pokemon.getType()).get();
        pokemonID.setName("Pik");
        pokemonID.setType("Volt");

        Pokemon updatePokemon = pokemonRepo.save(pokemonID);

        Assertions.assertThat(updatePokemon.getName()).isNotNull();
        Assertions.assertThat(updatePokemon.getType()).isNotNull();
    }

    @Test
    public void PokemonRepository_DeletePokemon_ReturnPokemon(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("elect")
                .build();

        pokemonRepo.save(pokemon);

        pokemonRepo.deleteById(pokemon.getId());

        Optional<Pokemon> pokemonID = pokemonRepo.findById(pokemon.getId());

        Assertions.assertThat(pokemonID).isEmpty();
    }
}
