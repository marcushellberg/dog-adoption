package com.example.service.ui;

import java.util.List;

import com.example.service.adoptions.Dog;
import com.example.service.adoptions.DogAdoptionService;
import com.example.service.adoptions.DogAdoptionSuggestion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("")
class AdoptionsView extends VerticalLayout {

    private final DogAdoptionService dogAdoptionService;
    private final VerticalLayout dogs = new VerticalLayout();
    private String query = "";

    class DogCard extends HorizontalLayout {

        DogCard(Dog dog) {
            var image = new Image(dog.image(), "dog");
            image.setWidth("100px");
            image.setHeight("100px");

            var name = new Span(dog.name());
            name.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.MEDIUM);

            var description = new Paragraph(dog.description());

            var ownerName = new TextField();
            ownerName.setPlaceholder("Your name");

            var adoptButton = new Button("Adopt");
            adoptButton.addClickListener(e -> {
                dogAdoptionService.adopt(dog.id(), ownerName.getValue());
                refresh();
            });

            var adoptionForm = new HorizontalLayout(ownerName, adoptButton);

            var owner = dog.owner() == null ? adoptionForm : new Span("Owner: " + dog.owner());

            add(image, new VerticalLayout(name, description, owner));
        }
    }

    AdoptionsView(DogAdoptionService dogAdoptionService) {
        this.dogAdoptionService = dogAdoptionService;
        setSizeFull();

        var queryField = new TextField();
        queryField.setPlaceholder("I want a dog that's ...");
        queryField.addValueChangeListener(e -> {
            query = e.getValue();
            refresh();
        });
        queryField.setClearButtonVisible(true);
        queryField.setValueChangeMode(ValueChangeMode.LAZY);

        add(new H1("Pooch Palace Dog Adoptions"), queryField);
        addAndExpand(new Scroller(dogs));
        refresh();
    }

    private void refresh() {
        dogs.removeAll();

        List<Integer> suggestedIds = query.isEmpty() ? List.of() : dogAdoptionService.suggest(query).stream().map(DogAdoptionSuggestion::id).toList();

        dogAdoptionService.all()
            .stream()
            .filter(dog -> suggestedIds.isEmpty() || suggestedIds.contains(dog.id()))
            .forEach(dog -> dogs.add(new DogCard(dog)));
    }

}
