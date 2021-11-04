package com.depaul.se433_project_stay_booking.repository;

import com.depaul.se433_project_stay_booking.model.Location;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends ElasticsearchRepository<Location, Long>, CustomLocationRepository {
//    List<Long> searchByDistance(double lat, double lon, String distance);
}
