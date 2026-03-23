package com.rakesh.bms.Service;


import com.rakesh.bms.Dto.MovieDto;
import com.rakesh.bms.Exception.ResourceNotFoundException;
import com.rakesh.bms.Model.Movie;
import com.rakesh.bms.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
   private  MovieRepository movieRepository ;


    public MovieDto createMovie(MovieDto movieDto){
        Movie movie=mapToEntity(movieDto);
        Movie saveMovie=movieRepository.save(movie);
        return mapToDto(saveMovie);
    }
    
    public MovieDto getMovieById(Long id)
    {
        Movie movie=movieRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Movie not found with id : "+id));
        return mapToDto(movie);
    }

    public List<MovieDto> getAllMovies()
    {
        List<Movie> movies=movieRepository.findAll();
        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<MovieDto> getMovieByLanguage(String language)
    {
        List<Movie> movies=movieRepository.findByLanguage(language);
        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<MovieDto> getMovieByGenre(String genre)
    {
        List<Movie> movies = movieRepository.findByGenre(genre);
        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    public List<MovieDto> searchMovies(String title)
    {
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(title);
        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public MovieDto updateMovie(Long id,MovieDto movieDto)
    {
        Movie movie=movieRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Movie not found with id : "+id));
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setLanguage(movieDto.getLanguage());
        movie.setGenre(movieDto.getGenre());
        movie.setDurationMins(movieDto.getDurationMins());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setPosterUrl(movieDto.getPosterUrl());

        Movie updatedMovie = movieRepository.save(movie);
        return mapToDto(updatedMovie);
    }

    public void deleteMovie(Long id)
    {
        Movie movie=movieRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Movie not found with id : "+id));
        movieRepository.delete(movie);
    }



    private MovieDto mapToDto(Movie movie)
    {
        MovieDto movieDto=new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setDescription(movie.getDescription());
        movieDto.setLanguage(movie.getLanguage());
        movieDto.setGenre(movie.getGenre());
        movieDto.setDurationMins(movie.getDurationMins());

        int mins = movie.getDurationMins();
        int hours = mins / 60;
        int remainingMins = mins % 60;
        String formatted = hours + " hr " + remainingMins + " mins";
        movieDto.setDurationformatted(formatted);

        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setPosterUrl(movie.getPosterUrl());
        return movieDto;
    }

    public Movie mapToEntity(MovieDto movieDto)
    {
        Movie movie=new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setLanguage(movieDto.getLanguage());
        movie.setGenre(movieDto.getGenre());
        movie.setDurationMins(movieDto.getDurationMins());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setPosterUrl(movieDto.getPosterUrl());
        return movie;
    }
    
}
