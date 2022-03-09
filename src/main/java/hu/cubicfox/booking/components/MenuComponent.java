package hu.cubicfox.booking.components;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MenuComponent extends VerticalLayout {

    public MenuComponent() {
        Anchor main = new Anchor();
        main.setText("Dashboard");
        main.setHref("/");
        add(main);

        Anchor hotels = new Anchor();
        hotels.setText("Hotels");
        hotels.setHref("/hotels");
        add(hotels);
    }

}
