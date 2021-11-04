package com.depaul.se433_project_stay_booking.repository;

import com.depaul.se433_project_stay_booking.model.Reservation;
import com.depaul.se433_project_stay_booking.model.Stay;
import com.depaul.se433_project_stay_booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByGuest(User guest);
    List<Reservation> findByStay(Stay stay);
    List<Reservation> findByStayAndCheckoutDateAfter(Stay stay, LocalDate date);
}
