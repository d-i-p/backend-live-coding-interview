package com.de.hiking;

import com.de.hiking.models.Booking;
import com.de.hiking.models.Hiker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BookingTests extends HikingApiApplicationTest {

    @DisplayName("Checks that the booking deletion works")
    @Test
    void checkDeleteBooking() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());

        Set members = new HashSet<Hiker>();

        Hiker h1 = new Hiker();
        h1.setHikerId(UUID.fromString("3c8097ef-ecef-43ea-ae43-3cb88cd7ab7e"));
        h1.setAge(15);
        members.add(h1);

        booking.setBookMembers(members);
        booking.setReservedByHikerId(UUID.fromString("9899a076-9d93-471e-b06e-3447bcaa5200"));
        booking.setTrailId(UUID.fromString("ac43d61a-9a62-4151-9448-f4b09dba6b54"));

        ResponseEntity<Booking> result = this.restTemplate.postForEntity(API_PATH + "/booking", booking, Booking.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());

        ResponseEntity<Object> resultDelete = this.restTemplate.exchange(
                API_PATH + "/booking/" + result.getBody().getBookingId(),
                HttpMethod.DELETE,
                new HttpEntity<>(""),
                Object.class);

        assertEquals(HttpStatus.OK, resultDelete.getStatusCode());
    }

    @DisplayName("Checks that the deletion of a non existing book does not work")
    @Test
    void checkDeleteNonExistingBooking() {

        ResponseEntity<Object> resultDelete = this.restTemplate.exchange(
                API_PATH + "/booking/" + UUID.randomUUID(),
                HttpMethod.DELETE,
                new HttpEntity<>(""),
                Object.class);

        assertEquals(HttpStatus.NOT_FOUND, resultDelete.getStatusCode());
    }


    @DisplayName("Checks that the booking creation works")
    @Test
    void checkPostBooking() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());

        //add members to booking
        Set members = new HashSet<Hiker>();

        Hiker h1 = new Hiker();
        h1.setHikerId(UUID.fromString("3c8097ef-ecef-43ea-ae43-3cb88cd7ab7e"));
        members.add(h1);

        Hiker h3 = new Hiker();
        h3.setHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));
        members.add(h3);

        booking.setBookMembers(members);
        booking.setReservedByHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));
        booking.setTrailId(UUID.fromString("c820b2b3-f10a-4ab9-86c0-e32362e2cc1d"));

        ResponseEntity<Booking> result = this.restTemplate.postForEntity(API_PATH + "/booking", booking, Booking.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        //the booking has its members
        assert (result.getBody().getBookMembers().size() == 2);

    }

    @DisplayName("Check that a book including a non existing hiker faults")
    @Test
    void checkPostBookingNonExistingMember() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());

        //add members to booking
        Set members = new HashSet<Hiker>();

        Hiker h1 = new Hiker();
        h1.setHikerId(UUID.randomUUID());
        members.add(h1);

        Hiker h3 = new Hiker();
        h3.setHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));
        members.add(h3);

        booking.setBookMembers(members);
        booking.setReservedByHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));
        booking.setTrailId(UUID.fromString("c820b2b3-f10a-4ab9-86c0-e32362e2cc1d"));

        ResponseEntity<Booking> result = this.restTemplate.postForEntity(API_PATH + "/booking", booking, Booking.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }

    @DisplayName("Check that a book including a hiker not respecting the age constraints fails")
    @Test
    void checkPostBookingAgeWrongMember() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());

        //add members to booking
        Set members = new HashSet<Hiker>();

        Hiker h2 = new Hiker();
        h2.setHikerId(UUID.fromString("a3e69f82-0645-4ae4-9f44-a46de25dbea6"));
        h2.setAge(15);
        members.add(h2);

        Hiker h3 = new Hiker();
        h3.setHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));
        h3.setAge(5);
        members.add(h3);

        booking.setBookMembers(members);

        booking.setTrailId(UUID.fromString("c820b2b3-f10a-4ab9-86c0-e32362e2cc1d"));
        booking.setReservedByHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));

        ResponseEntity<Booking> result = this.restTemplate.postForEntity(API_PATH + "/booking", booking, Booking.class);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }

    @DisplayName("Check that a book referring to a non existing trail fails")
    @Test
    void checkPostBookingAgeWrongTrail() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());

        //add members to booking
        Set members = new HashSet<Hiker>();

        Hiker h2 = new Hiker();
        h2.setHikerId(UUID.fromString("a3e69f82-0645-4ae4-9f44-a46de25dbea6"));
        members.add(h2);

        Hiker h3 = new Hiker();
        h3.setHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));
        members.add(h3);

        booking.setBookMembers(members);
        booking.setReservedByHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));

        ResponseEntity<Booking> result = this.restTemplate.postForEntity(API_PATH + "/booking" + UUID.randomUUID(), booking, Booking.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }

    @DisplayName("Check that a book made by a non existing hiker fails")
    @Test
    void checkPostBookingAgeWrongHiker() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());

        //add members to booking
        Set members = new HashSet<Hiker>();

        Hiker h2 = new Hiker();
        h2.setHikerId(UUID.fromString("a3e69f82-0645-4ae4-9f44-a46de25dbea6"));
        h2.setAge(25);
        members.add(h2);

        Hiker h3 = new Hiker();
        h3.setHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));
        h3.setAge(25);
        members.add(h3);

        booking.setBookMembers(members);
        booking.setReservedByHikerId(UUID.randomUUID());
        booking.setTrailId(UUID.fromString("c820b2b3-f10a-4ab9-86c0-e32362e2cc1d"));

        ResponseEntity<Booking> result = this.restTemplate.postForEntity(API_PATH + "/booking", booking, Booking.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }

    @DisplayName("Check that a book including no hikers fails")
    @Test
    void checkPostBookingEmptyMember() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());

        //add members to booking
        Set members = new HashSet<Hiker>();

        booking.setBookMembers(members);
        booking.setTrailId(UUID.fromString("c820b2b3-f10a-4ab9-86c0-e32362e2cc1d"));
        booking.setReservedByHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));

        ResponseEntity<Booking> result = this.restTemplate.postForEntity(API_PATH + "/booking", booking, Booking.class);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }


    @DisplayName("Checks that the booking retrieval per hiker works")
    @Test
    void checkGetBookingHiker() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());

        //add members to booking
        Set members = new HashSet<Hiker>();

        Hiker h1 = new Hiker();
        h1.setHikerId(UUID.fromString("3c8097ef-ecef-43ea-ae43-3cb88cd7ab7e"));
        members.add(h1);

        Hiker h3 = new Hiker();
        h3.setHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));
        members.add(h3);

        booking.setBookMembers(members);
        booking.setReservedByHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));
        booking.setTrailId(UUID.fromString("c820b2b3-f10a-4ab9-86c0-e32362e2cc1d"));

        ResponseEntity<Booking> result = this.restTemplate.postForEntity(API_PATH + "/booking", booking, Booking.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        //the booking has its members
        assert (result.getBody().getBookMembers().size() == 2);

        ResponseEntity<Booking[]> resultGet = this.restTemplate.getForEntity(API_PATH + "/booking?hikerId=d7be7688-01c4-4713-b273-3749ccd2a1ab", Booking[].class);

        //only bookings for the selected hiker
        Arrays.asList(resultGet.getBody()).stream().map(a -> a.getBookingId()).allMatch(a -> a.equals(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab")));

    }

    @DisplayName("Checks that the booking retrieval does not work with no params")
    @Test
    void checkGetBookingNoParam() {
        ResponseEntity<Object> resultGet = this.restTemplate.getForEntity(API_PATH + "/booking", Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, resultGet.getStatusCode());
    }

    @DisplayName("Checks that the booking retrieval does not work with both params")
    @Test
    void checkGetBookingBothParam() {
        ResponseEntity<Object> resultGet = this.restTemplate.getForEntity(API_PATH + "/booking?hikerId=" + UUID.randomUUID() + "&date=" + LocalDate.now(), Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, resultGet.getStatusCode());
    }

    @DisplayName("Checks that the retrieval of books belonging to a non existing hiker returns an error")
    @Test
    void checkGetBookingNonExistingHiker() {
        ResponseEntity<Object> resultGet = this.restTemplate.getForEntity(API_PATH + "/booking?hikerId=" + UUID.randomUUID(), Object.class);
        assertEquals(HttpStatus.NOT_FOUND, resultGet.getStatusCode());
    }

    @DisplayName("Checks that the retrieval of the bookings for a specific date works")
    @Test
    void checkGetBookingDate() {
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());

        //add members to booking
        Set members = new HashSet<Hiker>();

        Hiker h1 = new Hiker();
        h1.setHikerId(UUID.fromString("3c8097ef-ecef-43ea-ae43-3cb88cd7ab7e"));
        members.add(h1);

        Hiker h3 = new Hiker();
        h3.setHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));
        members.add(h3);

        booking.setBookMembers(members);
        booking.setReservedByHikerId(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab"));
        booking.setTrailId(UUID.fromString("c820b2b3-f10a-4ab9-86c0-e32362e2cc1d"));

        ResponseEntity<Booking> result = this.restTemplate.postForEntity(API_PATH + "/booking", booking, Booking.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        //the booking has its members
        assert (result.getBody().getBookMembers().size() == 2);

        ResponseEntity<Booking[]> resultGet = this.restTemplate.getForEntity(API_PATH + "/booking?date=" + LocalDate.now(), Booking[].class);

        //only bookings for the selected hiker
        Arrays.asList(resultGet.getBody()).stream().map(a -> a.getBookingId()).allMatch(a -> a.equals(UUID.fromString("d7be7688-01c4-4713-b273-3749ccd2a1ab")));

    }

}
