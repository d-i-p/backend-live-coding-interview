package com.de.hiking.service;


import com.de.hiking.models.Booking;
import com.de.hiking.models.Hiker;
import com.de.hiking.models.Trail;
import com.de.hiking.repository.BookingRepository;
import com.de.hiking.repository.HikerRepository;
import com.de.hiking.repository.TrailRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private BookingRepository bookingRepository;

    private TrailRepository trailRepository;

    private HikerRepository hikerRepository;

    public Booking createBooking(Booking booking) {
        if (verifyBooking(booking)) {
            return bookingRepository.save(booking);
        } else {
            ResponseStatusException re = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot book: one or more hiker do not respect the trail age constraints");
            logger.error(re.getMessage());
            throw re;
        }

    }

    public void deleteBooking(UUID bookingId) {
        if (bookingRepository.getByBookingId(bookingId) == null) {
            ResponseStatusException re = new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
            logger.error(re.getMessage());
            throw re;
        }
        bookingRepository.deleteById(bookingId);
    }

    public List<Booking> getBookings(LocalDate date, UUID hikerId) {

        if (date == null && hikerId == null) {
            ResponseStatusException re = new ResponseStatusException(HttpStatus.BAD_REQUEST, "No selection criteria for the booking: insert either a hikerId or a date");
            logger.error(re.getMessage());
            throw re;
        }
        if (date != null && hikerId != null) {
            ResponseStatusException re = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Select bookings either for a biker or for a date");
            logger.error(re.getMessage());
            throw re;
        }

        List<Booking> result;

        if (date != null) {
            result = bookingRepository.getBookingsByBookingDate(date);
        } else {
            result = bookingRepository.getBookingsByReservedByHikerId(hikerId);
        }
        if (result.size() == 0) {
            ResponseStatusException re = new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
            logger.error(re.getMessage());
            throw re;
        }
        return result;
    }

    private boolean verifyBooking(Booking booking) {
        boolean accept;

        Trail trail = trailRepository.getTrailByTrailId(booking.getTrailId());

        if (trail == null) {
            ResponseStatusException re = new ResponseStatusException(HttpStatus.NOT_FOUND, "Trail in the booking not found");
            logger.error(re.getMessage());
            throw re;
        }

        int ageLowerLimit = trail.getMinAge();
        int ageUpperLimit = trail.getMaxAge();

        Set<Hiker> hikers = booking.getBookMembers()
                .stream()
                .map(hiker -> {
                    Hiker hiker1 = hikerRepository.getByHikerId(hiker.getHikerId());
                    if (hiker1 == null) {
                        ResponseStatusException re = new ResponseStatusException(HttpStatus.NOT_FOUND, "Hiker in the booking not found");
                        logger.error(re.getMessage());
                        throw re;
                    }
                    return hiker1;
                })
                .collect(Collectors.toSet());

        if (hikers.isEmpty()) {
            ResponseStatusException re = new ResponseStatusException(HttpStatus.BAD_REQUEST, "No empty bookings allowed");
            logger.error(re.getMessage());
            throw re;
        }

        Hiker booker = hikerRepository.getByHikerId(booking.getReservedByHikerId());
        if (booker == null) {
            ResponseStatusException re = new ResponseStatusException(HttpStatus.NOT_FOUND, "Hiker making the booking not found");
            logger.error(re.getMessage());
            throw re;
        }

        Set<Hiker> membersToValidate = new HashSet<>();

        for (Hiker hiker : booking.getBookMembers()) {
            hiker = hikerRepository.getByHikerId(hiker.getHikerId());
            if (hiker == null) {
                ResponseStatusException re = new ResponseStatusException(HttpStatus.NOT_FOUND, "Hiker inside the booking not found");
                logger.error(re.getMessage());
                throw re;
            }
            membersToValidate.add(hiker);
        }

        booking.setBookMembers(membersToValidate);
        accept = hikers.stream().allMatch(a -> a.getAge() >= ageLowerLimit && a.getAge() <= ageUpperLimit);
        logger.info("Booking accepted: " + accept);
        return accept;

    }

}
