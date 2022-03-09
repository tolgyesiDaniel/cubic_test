package hu.cubicfox.booking.components;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import hu.cubicfox.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class MainLayout extends AppLayout {

    @Autowired
    private UserService userService;

    public MainLayout(){
        createHeader();
        createDrawer();
    }

    private void createHeader(){
        Span logo = new Span("Booking");
        logo.addClassName("logo");

        Anchor logout = new Anchor("logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.setPadding(true);
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("98%");
        header.addClassName("header");

        addToNavbar(header);
    }

    private void createDrawer(){
        addToDrawer(new MenuComponent());
    }

}
