package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.repository.AvatarRepository;

@Service
public class AvatarService {

    private final AvatarRepository repository;

    public AvatarService(AvatarRepository repository) {
        this.repository = repository;
    }
}
