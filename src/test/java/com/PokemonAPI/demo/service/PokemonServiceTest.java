package com.PokemonAPI.demo.service;

import com.PokemonAPI.demo.dto.PokemonDto;
import com.PokemonAPI.demo.dto.PokemonResponse;
import com.PokemonAPI.demo.model.Pokemon;
import com.PokemonAPI.demo.repository.PokemonRepo;
import com.PokemonAPI.demo.service.impl.PokemonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    @Mock
    private PokemonRepo pokemonRepo;//انشانا نسخة وهمية من repo عشان ما نلعب في البيانات الاساسية

    @InjectMocks
    private PokemonServiceImpl pokemonServiceImpl;

    @Test
    public void PokemonService_CreatePokemon_ReturnsPokemonDto(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("elect")
                .build();

        PokemonDto pokemonDto = PokemonDto.builder()
                .name("pikachu")
                .type("elect")
                .build();

        when(pokemonRepo.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto savedPokemon = pokemonServiceImpl.createPokemon(pokemonDto);

        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void PokemonService_GetAllPokemon_ReturnResponseDto(){
        Page<Pokemon> pokemons = Mockito.mock(Page.class);

        when(pokemonRepo.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponse savePokemon = pokemonServiceImpl.getAllPokemon(1 , 10);

        Assertions.assertThat(savePokemon).isNotNull();
    }

    @Test
    public void PokemonService_GetPokemonById(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("elect")
                .build();

        when(pokemonRepo.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        PokemonDto savedPokemon = pokemonServiceImpl.getPokemonById(1);

        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void PokemonService_updatePokemonById(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("elect")
                .build();

        PokemonDto pokemonDto = PokemonDto.builder()
                .name("pikachu")
                .type("elect")
                .build();

        when(pokemonRepo.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        when(pokemonRepo.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto savedPokemon = pokemonServiceImpl.updatePokemonById(pokemonDto,1);

        Assertions.assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void PokemonService_deletePokemonById(){
        Pokemon pokemon = Pokemon.builder()
                .name("pikachu")
                .type("elect")
                .build();

        when(pokemonRepo.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        assertAll(() -> pokemonServiceImpl.deletePokemon(1));
    }
}
