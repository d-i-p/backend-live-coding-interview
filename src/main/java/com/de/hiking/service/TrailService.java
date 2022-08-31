package com.de.hiking.service;

import com.de.hiking.models.Trail;
import com.de.hiking.repository.TrailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class TrailService {

    TrailRepository trailRepository;

    public List<Trail> getTrails() {
        return trailRepository.findAll();
    }

}
