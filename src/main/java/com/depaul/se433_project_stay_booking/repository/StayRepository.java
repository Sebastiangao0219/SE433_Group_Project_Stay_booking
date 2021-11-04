package com.depaul.se433_project_stay_booking.repository;

import com.depaul.se433_project_stay_booking.model.Stay;
import com.depaul.se433_project_stay_booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StayRepository extends JpaRepository <Stay, Long>{
    List<Stay> findByHost(User user);
    List<Stay> findByIdInAndGuestNumberGreaterThanEqual(List<Long> ids, int guestNumber);
}
// 为什么要继承 JpaRepository？
// 继承后就有了一些操作数据库的基本方法