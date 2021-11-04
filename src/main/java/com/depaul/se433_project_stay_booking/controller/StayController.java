package com.depaul.se433_project_stay_booking.controller;

import com.depaul.se433_project_stay_booking.model.Reservation;
import com.depaul.se433_project_stay_booking.model.Stay;
import com.depaul.se433_project_stay_booking.model.User;
import com.depaul.se433_project_stay_booking.service.ReservationService;
import com.depaul.se433_project_stay_booking.service.StayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StayController {
    private StayService stayService;
    private ReservationService reservationService;

    @Autowired
    public StayController(StayService stayService, ReservationService reservationService) {
        this.stayService = stayService;
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/stays/reservations/{stayId}")
    public List<Reservation> listReservations(@PathVariable Long stayId, Principal principal) {
        return reservationService.listByStay(stayId);
    }


    @GetMapping(value = "/stays")
    public List<Stay> listStays(Principal principal) {
        return stayService.listByUser(principal.getName());
    }

    @GetMapping(value = "/stays/{stayId}")
    public Stay getStay(@PathVariable Long stayId) {
        return stayService.findById(stayId);
    }

    @PostMapping("/stays")
    public void addStay(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("description") String description,
//            @RequestParam("host") String host,
            @RequestParam("guest_number") int guestNumber,
//            @RequestParam("images") MultipartFile[] images) {
            @RequestParam("images") MultipartFile[] images,
            Principal principal) {

        Stay stay = new Stay.Builder().setName(name)
                .setAddress(address)
                .setDescription(description)
                .setGuestNumber(guestNumber)
//                .setHost(new User.Builder().setUsername(host).build())
                .setHost(new User.Builder().setUsername(principal.getName()).build())
                .build();
        stayService.add(stay, images);
    }

    @DeleteMapping("/stays/{stayId}")
    public void deleteStay(@PathVariable Long stayId) {
        stayService.delete(stayId);
    }
}
