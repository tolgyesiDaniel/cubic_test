package hu.cubicfox.booking.components;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Dashboard")
public class MainView extends HorizontalLayout {

    @PostConstruct
    public void init(){
        add(new H3("Dashboard"));
    }

}
