package com.rakesh.bms.Controller;


import com.rakesh.bms.Dto.MovieDto;
import com.rakesh.bms.Dto.ShowDto;
import com.rakesh.bms.Service.ShowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
@CrossOrigin(origins = "http://localhost:5173")
public class ShowController {

    @Autowired
    ShowService showService;

    @PostMapping
    public ResponseEntity<ShowDto> createShow(@Valid @RequestBody ShowDto showDto){
        return new ResponseEntity<>(showService.creteShow(showDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowDto> getShowById(@PathVariable Long id){
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @GetMapping
    public ResponseEntity<List<ShowDto>> getAllShows(){
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowDto>> getShowsByMovie(@PathVariable Long movieId){
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping("/movie/{movieId}/city/{city}")
    public ResponseEntity<List<ShowDto>> getShowsByMovieAndCity(@PathVariable Long movieId,
                                                                @PathVariable String city){
        return ResponseEntity.ok(showService.getShowsByMovieAndCity(movieId, city));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ShowDto>> getShowsByDateRange(@RequestParam LocalDateTime startDate,
                                                             @RequestParam LocalDateTime endDate){
        return ResponseEntity.ok(showService.getShowsByDateRange(startDate, endDate));
    }
}