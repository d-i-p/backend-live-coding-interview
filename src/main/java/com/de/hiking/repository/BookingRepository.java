package com.de.hiking.repository;

import com.de.hiking.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> getBookingsByReservedByHikerId(UUID hikerId);

    Booking getByBookingId(UUID bookingId);

    void deleteById(UUID uuid);

    List<Booking> getBookingsByBookingDate(LocalDate date);

    Booking save(Booking booking);

}
