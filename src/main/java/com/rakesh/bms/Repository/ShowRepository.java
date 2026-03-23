package com.rakesh.bms.Repository;

import com.rakesh.bms.Model.Booking;
import com.rakesh.bms.Model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show,Long> {

    List<Show> findByMovieId(Long movieId);
    List<Show> findByScreenId(Long screenId);

    List<Show> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Show> findByMovie_IdAndScreen_Theater_City(Long moiveId,String city);


}
