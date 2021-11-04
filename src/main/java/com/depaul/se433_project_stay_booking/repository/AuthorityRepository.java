package com.depaul.se433_project_stay_booking.repository;

import com.depaul.se433_project_stay_booking.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
