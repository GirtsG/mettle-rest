package com.accenture.mettle.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "featureId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Feature {

    @Id
    @GeneratedValue
    private Long featureId;
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "features")
    @JsonIgnore
    private Set<User> users;
}
