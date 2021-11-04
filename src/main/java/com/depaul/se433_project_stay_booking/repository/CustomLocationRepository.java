package com.depaul.se433_project_stay_booking.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomLocationRepository {
    List<Long> searchByDistance(double lat, double lon, String distance);
}
