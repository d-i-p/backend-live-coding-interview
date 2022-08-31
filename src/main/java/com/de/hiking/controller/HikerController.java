package com.de.hiking.controller;

import com.de.hiking.models.Hiker;
import com.de.hiking.service.HikerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Api(value = "/api/hiking", tags = {"Hiking API"})
@RequestMapping("/api/hiking")
@Controller
public class HikerController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    HikerService hikerService;

    public HikerController(HikerService hikerService) {
        this.hikerService = hikerService;
    }

    @PostMapping(value = "/hiker")
    @ApiOperation(value = "Hiker registration", notes = "Register a hiker")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Hiker.class)})
    @ResponseBody
    public Hiker createHiker(@RequestBody Hiker hiker) {
        Hiker result = hikerService.createHiker(hiker);
        logger.info("New hiker created: " + result);
        return result;
    }


    @DeleteMapping(value = "/hiker/{hikerId}")
    @ApiOperation(value = "Hiker deletion", notes = "Delete a hiker")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = ResponseEntity.class),
            @ApiResponse(code = 404, message = "Hiker not found")})
    @ResponseBody
    public ResponseEntity<HttpStatus> deleteHiker(@PathVariable UUID hikerId) {
        hikerService.deleteHiker(hikerId);
        logger.info("Hiker with id: " + hikerId + " deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/hikers")
    @ApiOperation(value = "Hikers retrieval", notes = "retrieve all stored hikers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Hiker.class, responseContainer = "list")})
    @ResponseBody
    public List<Hiker> getHikers() {
        List<Hiker> result = hikerService.getHikers();
        logger.info("Hikers retrieved: ");
        result.forEach(hiker -> logger.info(hiker.toString()));
        return result;
    }

    @GetMapping(value = "/hiker/{hikerId}")
    @ApiOperation(value = "Hikers retrieval", notes = "retrieve all stored hikers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Hiker.class),
            @ApiResponse(code = 404, message = "Hiker not found")
    })
    @ResponseBody
    public Hiker getHiker(@PathVariable UUID hikerId) {
        Hiker result = hikerService.getHiker(hikerId);
        logger.info("Hiker retrieved: " + result);
        return result;
    }
}
