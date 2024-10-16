package com.example.service.ui;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.menu.MenuConfiguration;

@Layout
class MainLayout extends VerticalLayout implements RouterLayout {

    MainLayout() {
        var menu = new HorizontalLayout();
        MenuConfiguration.getMenuEntries().forEach(entry -> {
            var link = new RouterLink(entry.title(), entry.menuClass());
            menu.add(link);
        });

        add(menu);
    }
}
