package com.accenture.mettle.controller;

import com.accenture.mettle.entity.Feature;
import com.accenture.mettle.entity.User;
import com.accenture.mettle.repository.FeatureRepository;
import com.accenture.mettle.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("features")
@AllArgsConstructor
public class FeatureController {

    private final FeatureRepository featureRepository;
    private final UserRepository userRepository;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    Feature createNew(@RequestBody Feature feature) {
        return featureRepository.save(feature);
    }

    @PutMapping("/{id}/user/{username}")
    void enableForUser(@PathVariable Long id, @PathVariable String username) {
        Feature feature = featureRepository.getById(id);
        User user = userRepository.findByName(username);
        requireNonNull(user, "User by username `" + username + "` not found!");
        user.addFeature(feature);
        userRepository.save(user);
    }

    @DeleteMapping("/{id}/user/{username}")
    void disableForUser(@PathVariable Long id, @PathVariable String username) {
        Feature feature = featureRepository.getById(id);
        User user = userRepository.findByName(username);
        user.removeFeature(feature);
        userRepository.save(user);
    }

    @GetMapping("/enabled")
    List<Feature> getEnabled() {
        return featureRepository.findByUsersIsNotNull();
    }
}
