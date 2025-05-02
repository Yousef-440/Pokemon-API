package com.PokemonAPI.demo.service.impl;

import com.PokemonAPI.demo.dto.PokemonDto;
import com.PokemonAPI.demo.dto.PokemonResponse;
import com.PokemonAPI.demo.exceptions.PokemonNotFoundException;
import com.PokemonAPI.demo.model.Pokemon;
import com.PokemonAPI.demo.repository.PokemonRepo;
import com.PokemonAPI.demo.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {
    @Autowired
    private PokemonRepo repo;

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = Pokemon.builder().name(pokemonDto.getName()).type(pokemonDto.getType()).build();
        Pokemon newPokemon = repo.save(pokemon);

        PokemonDto newPokemonDto = PokemonDto.builder()
                .id(newPokemon.getId()).name(newPokemon.getName()).type(newPokemon.getType()).build();

        return newPokemonDto;
    }

    @Override
    public PokemonResponse getAllPokemon(int pageNo , int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemon = repo.findAll(pageable);
        List<Pokemon> listOfPokemon = pokemon.getContent();
        if(pokemon.isEmpty()){
            throw new PokemonNotFoundException("Sorry, did not find any Pokemon");
        }
        List<PokemonDto>content = listOfPokemon.stream().map(pok->mapToDto(pok)).collect(Collectors.toList());

        PokemonResponse pokemonResponse = PokemonResponse.builder()
                .content(content)
                .pageNo(pageNo).pageSize(pageSize)
                .totalElements(pokemon.getTotalElements())
                .totalPages(pokemon.getTotalPages())
                .last(pokemon.isLast())
                .build();

        return pokemonResponse;
    }

    @Override
    public PokemonDto getPokemonById(int id) {
        Pokemon pokemon = repo.findById(id).orElseThrow(()->new PokemonNotFoundException("Sorry,pokemon of {" + id + "} Not found"));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemonById(PokemonDto pokemonDto, int id) {
        Pokemon pokemon = repo.findById(id).orElseThrow(()-> new PokemonNotFoundException("Sorry,pokemon of {" + id + "} Not found"));
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        Pokemon updated = repo.save(pokemon);

        return mapToDto(updated);
    }

    @Override
    public void deletePokemon(int id) {
        Pokemon pokemon = repo.findById(id).orElseThrow(()->new PokemonNotFoundException("Sorry, Pokemon Not Found"));
        repo.delete(pokemon);
    }

    private PokemonDto mapToDto(Pokemon pokemon){
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return pokemonDto;
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }
}