package com.rakesh.bms.Repository;

import com.rakesh.bms.Model.Booking;
import com.rakesh.bms.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie>findByLanguage(String language);

    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByGenre(String genre);


}
