package com.depaul.se433_project_stay_booking.service;

import com.depaul.se433_project_stay_booking.model.Stay;
import com.depaul.se433_project_stay_booking.repository.LocationRepository;
import com.depaul.se433_project_stay_booking.repository.StayAvailabilityRepository;
import com.depaul.se433_project_stay_booking.repository.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class SearchService {
    private StayRepository stayRepository;
    private StayAvailabilityRepository stayAvailabilityRepository;
    private LocationRepository locationRepository;

    @Autowired
    public SearchService(StayRepository stayRepository, StayAvailabilityRepository stayAvailabilityRepository, LocationRepository locationRepository) {
        this.stayRepository = stayRepository;
        this.stayAvailabilityRepository = stayAvailabilityRepository;
        this.locationRepository = locationRepository;
    }

    public List<Stay> search(int guestNumber, LocalDate checkinDate, LocalDate checkoutDate, double lat, double lon, String distance) {
        List<Long> stayIds = locationRepository.searchByDistance(lat, lon, distance);
        long duration = Duration.between(checkinDate.atStartOfDay(), checkoutDate.atStartOfDay()).toDays();

        List<Long> filteredStayIds = stayAvailabilityRepository.findByDateBetweenAndStateIsAvailable(stayIds, checkinDate, checkoutDate.minusDays(1), duration);
        return stayRepository.findByIdInAndGuestNumberGreaterThanEqual(filteredStayIds, guestNumber);
    }
}
