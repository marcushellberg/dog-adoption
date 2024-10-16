package com.example.service.ui;

import com.example.service.adoptions.Dog;
import com.example.service.adoptions.DogAdoptionService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Route("")
@Menu(title = "Adoptions")
public class AdoptionsView extends VerticalLayout {

    public AdoptionsView(DogAdoptionService dogAdoptionService) {
        var grid = new Grid<>(Dog.class);
        grid.setItems(dogAdoptionService.all());
        add(grid);
    }

}
