package com.depaul.se433_project_stay_booking.service;

import com.depaul.se433_project_stay_booking.exception.ReservationCollisionException;
import com.depaul.se433_project_stay_booking.exception.ReservationNotFoundException;
import com.depaul.se433_project_stay_booking.model.Reservation;
import com.depaul.se433_project_stay_booking.model.Stay;
import com.depaul.se433_project_stay_booking.model.User;
import com.depaul.se433_project_stay_booking.repository.ReservationRepository;
import com.depaul.se433_project_stay_booking.repository.StayAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private StayAvailabilityRepository stayAvailabilityRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, StayAvailabilityRepository stayAvailabilityRepository) {
        this.reservationRepository = reservationRepository;
        this.stayAvailabilityRepository = stayAvailabilityRepository;
    }

    public List<Reservation> listByGuest(String username) {
        return reservationRepository.findByGuest(new User.Builder().setUsername(username).build());
    }

    public List<Reservation> listByStay(Long stayId) {
        return reservationRepository.findByStay(new Stay.Builder().setId(stayId).build());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(Reservation reservation) throws ReservationCollisionException {
        List<LocalDate> dates = stayAvailabilityRepository.countByDateBetweenAndId(reservation.getStay().getId(), reservation.getCheckinDate(), reservation.getCheckoutDate().minusDays(1));
        int duration = (int) Duration.between(reservation.getCheckinDate().atStartOfDay(), reservation.getCheckoutDate().atStartOfDay()).toDays();
        if (duration != dates.size()) {
            throw new ReservationCollisionException("Duplicate reservation");
        }
        stayAvailabilityRepository.reserveByDateBetweenAndId(reservation.getStay().getId(), reservation.getCheckinDate(), reservation.getCheckoutDate().minusDays(1));
        reservationRepository.save(reservation);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(Long reservationId) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ReservationNotFoundException("Reservation is not available"));
        stayAvailabilityRepository.cancelByDateBetweenAndId(reservation.getStay().getId(), reservation.getCheckinDate(), reservation.getCheckoutDate().minusDays(1));
        reservationRepository.deleteById(reservationId);
    }

}