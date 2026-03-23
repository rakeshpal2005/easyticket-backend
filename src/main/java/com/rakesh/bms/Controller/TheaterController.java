package com.rakesh.bms.Controller;


import com.rakesh.bms.Dto.MovieDto;
import com.rakesh.bms.Dto.TheaterDto;
import com.rakesh.bms.Service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
@CrossOrigin(origins = "http://localhost:5173")
public class TheaterController {


    @Autowired
    TheaterService theaterService;

    @PostMapping("/add")
    public ResponseEntity<TheaterDto> createTheater(@Valid @RequestBody TheaterDto theaterDto){
        return new ResponseEntity<>(theaterService.createTheater(theaterDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheaterDto> getTheaterById( @PathVariable Long id)
    {
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }


    @GetMapping
    public ResponseEntity<List<TheaterDto>> getAllTheaters()
    {
        return ResponseEntity.ok(theaterService.getAllTheaters());
    }

    @GetMapping("/city")
    public ResponseEntity<List<TheaterDto>> getAllTheaterByCity(@RequestParam  String city)
    {
        return ResponseEntity.ok(theaterService.getAllTheaterByCity(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TheaterDto> updateTheater(@PathVariable Long id,
                                                @RequestBody TheaterDto theaterDto){
        return ResponseEntity.ok(theaterService.updateTheater(id, theaterDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater( @PathVariable Long id){
        theaterService.deleteTheater(id);
        return ResponseEntity.noContent().build();
    }

}
