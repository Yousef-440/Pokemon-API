package com.PokemonAPI.demo.controller;

import com.PokemonAPI.demo.dto.PokemonDto;
import com.PokemonAPI.demo.dto.PokemonResponse;
import com.PokemonAPI.demo.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("pokemon")
    public ResponseEntity<PokemonResponse> getAllPokemon(
            @RequestParam(value = "pageNo" , defaultValue = "0" , required = false) int pageNo ,
            @RequestParam(value = "pageSize" , defaultValue = "10" , required = false )int pageSize
    ){
        return new ResponseEntity<>(pokemonService.getAllPokemon(pageNo , pageSize) , HttpStatus.OK);
    }

    @GetMapping("pokemon/{id}")
    public ResponseEntity<PokemonDto> getPokemon(@PathVariable int id){
        return ResponseEntity.ok(pokemonService.getPokemonById(id));
    }

    @PostMapping("pokemon/create")
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto){
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto) , HttpStatus.CREATED);
    }

    @PutMapping("pokemon/{id}/update")
    public ResponseEntity<PokemonDto> Update(@RequestBody PokemonDto pokemonDto , @PathVariable("id") int pokemonId){
        PokemonDto response = pokemonService.updatePokemonById(pokemonDto , pokemonId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("pokemon/{id}/delete")
    public ResponseEntity<String> DeletePokemon(@PathVariable("id") int pokemonId){
        pokemonService.deletePokemon(pokemonId);
        return ResponseEntity.ok("Pokemon Deleted Successfully");
    }
}
