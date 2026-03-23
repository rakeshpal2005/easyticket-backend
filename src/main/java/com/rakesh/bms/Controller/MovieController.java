package com.rakesh.bms.Controller;


import com.rakesh.bms.Dto.MovieDto;
import com.rakesh.bms.Service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/create")
    public ResponseEntity<MovieDto> createMovie(@Valid  @RequestBody MovieDto movieDto ){
        return new ResponseEntity<>(movieService.createMovie(movieDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id)
    {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies()
    {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<MovieDto>> getMovieByLanguage(@PathVariable String language)
    {
        return ResponseEntity.ok(movieService.getMovieByLanguage(language));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieDto>> getMovieByGenre(@PathVariable String genre)
    {
        return ResponseEntity.ok(movieService.getMovieByGenre(genre));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDto>> searchMovies(@RequestParam String title)
    {
        return ResponseEntity.ok(movieService.searchMovies(title));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id,
                                                @RequestBody MovieDto movieDto){
        return ResponseEntity.ok(movieService.updateMovie(id, movieDto));
    }

}
