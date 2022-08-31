package com.de.hiking.controller;

import com.de.hiking.models.Trail;
import com.de.hiking.service.TrailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Api(value = "/api/hiking", tags = {"Hiking API"})
@RequestMapping("/api/hiking")
@Controller
public class TrailController {

    private static final Logger logger = LoggerFactory.getLogger(TrailController.class);

    TrailService trailService;

    public TrailController(TrailService trailService) {
        this.trailService = trailService;
    }

    @GetMapping(value = "/trails")
    @ApiOperation(value = "Trails retrieval", notes = "retrieve all stored trails")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Trail.class, responseContainer = "list")})
    @ResponseBody
    public List<Trail> getTrails() {
        List<Trail> result = trailService.getTrails();
        logger.info("Trails retrieved: ");
        result.forEach(trail -> logger.info(trail.toString()));
        return result;
    }


}
