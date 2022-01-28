package com.accenture.mettle.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "features")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue
    private Long userId;
    @Column(unique = true)
    private String name;
    @ManyToMany
    private Set<Feature> features;

    public void addFeature(Feature feature) {
        if (!this.features.contains(feature)) {
            this.features.add(feature);
        }
    }

    public void removeFeature(Feature feature) {
        this.features.remove(feature);
    }
}
