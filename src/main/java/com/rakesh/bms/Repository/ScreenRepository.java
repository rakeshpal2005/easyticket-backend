package com.rakesh.bms.Repository;

import com.rakesh.bms.Model.Booking;
import com.rakesh.bms.Model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ScreenRepository extends JpaRepository<Screen,Long> {

    List<Screen> findByTheaterId(Long theaterId);
}
