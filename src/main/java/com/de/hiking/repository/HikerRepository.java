package com.de.hiking.repository;

import com.de.hiking.models.Hiker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HikerRepository extends JpaRepository<Hiker, UUID> {

    Hiker save(Hiker hiker);

    void deleteById(UUID uuid);

    List<Hiker> findAll();

    Hiker getByHikerId(UUID hikerId);

}
