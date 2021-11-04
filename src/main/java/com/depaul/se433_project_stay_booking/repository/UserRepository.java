package com.depaul.se433_project_stay_booking.repository;

import com.depaul.se433_project_stay_booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository                                        // user, id 类型
public interface UserRepository extends JpaRepository<User, String> {
}
