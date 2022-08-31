package com.de.hiking.repository;

import com.de.hiking.models.Trail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrailRepository extends JpaRepository<Trail, UUID> {
    List<Trail> findAll();
    Trail getTrailByTrailId(UUID trailId);
}
