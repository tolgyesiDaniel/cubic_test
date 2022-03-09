package hu.cubicfox.booking.components;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import hu.cubicfox.booking.security.SecurityUtils;

public class MenuComponent extends VerticalLayout {

    public MenuComponent() {
        Anchor main = new Anchor();
        main.setText("Dashboard");
        main.setHref("/");
        add(main);

        if(SecurityUtils.isAdmin()){
            Anchor user = new Anchor();
            user.setText("Users");
            user.setHref("/user");
            add(user);
        }
    }

}
