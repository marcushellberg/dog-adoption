package com.example.service.adoptions;

import java.util.Collection;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.ApplicationEventPublisher;
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

    public void adopt(int dogId, String ownerName) {
        this.repository.findById(dogId)
            .ifPresent(dog -> {
                var newDog = this.repository.save(new Dog(dog.id(), dog.name(), dog.description(), ownerName, dog.image()));
                System.out.println("adopted [" + newDog + "]");
                this.publisher.publishEvent(new DogAdoptionEvent(newDog.id()));
            });
    }

    public Collection<Dog> all() {
        return this.repository.findAll();
    }

    public List<DogAdoptionSuggestion> suggest(String query) {
        return this.singularity.prompt()
            .user("Do you have dogs that are " + query + "?")
            .call()
            .entity(DogAdoptionSuggestionResponse.class)
            .suggestions();
    }
}

record DogAdoptionSuggestionResponse(List<DogAdoptionSuggestion> suggestions) {

}

interface DogRepository extends ListCrudRepository<Dog, Integer> {

}

