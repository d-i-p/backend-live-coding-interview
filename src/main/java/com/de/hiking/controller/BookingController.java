package com.de.hiking.controller;

import com.de.hiking.models.Booking;
import com.de.hiking.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Api(value = "/api/hiking/booking", tags = {"Hiking API"})
@RequestMapping("/api/hiking/booking")
@Controller
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ApiOperation(value = "Booking creation", notes = "Make a booking")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Booking.class),
            @ApiResponse(code = 404, message = "A hiker inside the booking does not exist"),
            @ApiResponse(code = 400, message = "Bad request")})
    @ResponseBody
    public Booking createBooking(@RequestBody Booking booking) {
        Booking result = bookingService.createBooking(booking);
        logger.info("New book created: " + result);
        return result;
    }

    @DeleteMapping(value = "/{bookingId}")
    @ApiOperation(value = "Booking deletion", notes = "Delete a booking")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Booking not found")})
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteBooking(@PathVariable UUID bookingId) {
        bookingService.deleteBooking(bookingId);
        logger.info("Booking with Id" + bookingId + " deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "Booking retrieval from a Hiker or for a specific date", notes = "Get all the bookings from a Hiker or for a specific date")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Booking.class, responseContainer = "list")})
    @ResponseBody
    public List<Booking> getBookings(@RequestParam(name = "date", required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                     @RequestParam(name = "hikerId", required = false) UUID hikerId) {
        List<Booking> result = bookingService.getBookings(date, hikerId);
        logger.info("Bookings retrieved: ");
        result.forEach(booking -> logger.info(booking.toString()));
        return result;
    }

}
