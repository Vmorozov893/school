package ru.hogwarts.school.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;

    public Avatar(Long id) {
        this.id = id;
    }
}
