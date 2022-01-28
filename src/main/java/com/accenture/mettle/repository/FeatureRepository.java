package com.accenture.mettle.repository;

import com.accenture.mettle.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    List<Feature> findByUsersIsNotNull();
}
