package com.accenture.mettle.controller;

import com.accenture.mettle.entity.Feature;
import com.accenture.mettle.entity.User;
import com.accenture.mettle.repository.FeatureRepository;
import com.accenture.mettle.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class FeatureControllerTest {

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldBeNewFeatureCreated() {
        Feature feature = new Feature(1L, "FEATURE_1", null);

        ResponseEntity<Feature> response = restTemplate.postForEntity(
                "/features",
                feature,
                Feature.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new Feature(1L, "FEATURE_1", null));
    }

    @Test
    public void shouldBeFeatureEnabledForUser() {
        Long userId = 1L;
        String username = "user1";
        Long featureId = 1L;
        String featureName = "feature 1";
        userRepository.saveAndFlush(new User(userId, username, null));
        featureRepository.saveAndFlush(new Feature(featureId, featureName, null));


        ResponseEntity<Void> response = restTemplate.exchange("/features/" + featureId + "/user/" + username, HttpMethod.PUT, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(!userRepository.findByName(username).getFeatures().isEmpty());
    }

    @Test
    public void shouldBeFeatureDisabledForUser() {
        Long userId = 1L;
        String username = "user1";
        Long featureId = 1L;
        String featureName = "feature 1";
        userRepository.saveAndFlush(new User(userId, username, null));
        featureRepository.saveAndFlush(new Feature(featureId, featureName, null));

        ResponseEntity<Void> response = restTemplate.exchange("/features/" + featureId + "/user/" + username, HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userRepository.findByName(username).getFeatures().isEmpty());
    }

    @Test
    public void shouldGetEnabledFeatures() {
        Long userId = 1L;
        String username = "user1";
        Long featureId1 = 2L;
        String featureName1 = "feature 1";
        Long featureId2 = 3L;
        String featureName2 = "feature 2";
        User user = userRepository.saveAndFlush(new User(userId, username, null));
        featureRepository.save(new Feature(featureId1, featureName1, Set.of(user)));
        featureRepository.save(new Feature(featureId2, featureName2, null));

        ResponseEntity<List<Feature>> response = restTemplate.exchange("/features/enabled", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        }, Collections.emptyMap());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size() == 1);
        assertThat(response.getBody().contains(new Feature(featureId1, featureName1, Set.of(user))));
    }
}