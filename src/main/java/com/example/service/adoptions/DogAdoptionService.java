package com.example.service.adoptions;

import java.util.Collection;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DogAdoptionService {

    private final ChatClient singularity;
    private final DogRepository repository;
    private final ApplicationEventPublisher publisher;

    DogAdoptionService(ChatClient singularity, DogRepository repository, ApplicationEventPublisher publisher) {
        this.singularity = singularity;
        this.repository = repository;
        this.publisher = publisher;
    }

    void adopt(int dogId, String ownerName) {
        this.repository.findById(dogId)
            .ifPresent(dog -> {
                var newDog = this.repository.save(new Dog(dog.id(), dog.name(), dog.description(), ownerName));
                System.out.println("adopted [" + newDog + "]");
                this.publisher.publishEvent(new DogAdoptionEvent(newDog.id()));
            });
    }

    public Collection<Dog> all() {
        return this.repository.findAll();
    }

    DogAdoptionSuggestion suggest(String query) {
        return this.singularity
            .prompt()
            .user(query)
            .call()
            .entity(DogAdoptionSuggestion.class);
    }
}


interface DogRepository extends ListCrudRepository<Dog, Integer> {
}

