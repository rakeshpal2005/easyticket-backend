package com.rakesh.bms.Repository;

import com.rakesh.bms.Model.Booking;
import com.rakesh.bms.Model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {

    //List<ShowSeat> findByShowId(Long movieId);

    List<ShowSeat> findByShowIdAndStatus(Long showId,String status);

    List<ShowSeat> findByShowId(Long showId);

}
