package com.rakesh.bms.Repository;

import com.rakesh.bms.Model.Booking;
import com.rakesh.bms.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
