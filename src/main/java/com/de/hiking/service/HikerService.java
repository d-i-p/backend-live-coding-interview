package com.de.hiking.service;

import com.de.hiking.models.Hiker;
import com.de.hiking.repository.HikerRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HikerService {

    private static final Logger logger = LoggerFactory.getLogger(HikerService.class);

    private HikerRepository hikerRepository;

    public Hiker createHiker(Hiker hiker) {
        return hikerRepository.save(hiker);
    }

    public void deleteHiker(UUID hikerId) {
        if (hikerRepository.getByHikerId(hikerId) == null) {
            ResponseStatusException re = new ResponseStatusException(HttpStatus.NOT_FOUND, "Hiker not found");
            logger.error(re.getMessage());
            throw re;
        }
        hikerRepository.deleteById(hikerId);
    }

    public List<Hiker> getHikers() {
        return hikerRepository.findAll();
    }

    public Hiker getHiker(UUID hikerId) {
        Hiker result = hikerRepository.getByHikerId(hikerId);
        if (result == null) {
            ResponseStatusException re = new ResponseStatusException(HttpStatus.NOT_FOUND, "Hiker inside the booking not found");
            logger.error(re.getMessage());
            throw re;
        }
        return result;
    }

}
