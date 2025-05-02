package com.PokemonAPI.demo.service;

import com.PokemonAPI.demo.dto.PokemonDto;
import com.PokemonAPI.demo.dto.PokemonResponse;
import org.springframework.stereotype.Service;


public interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonResponse getAllPokemon(int pageNo , int pageSize);
    PokemonDto getPokemonById(int id);
    PokemonDto updatePokemonById(PokemonDto pokemonDto , int id);
    void deletePokemon(int id);
}
